package com.locotoinnovations.composelearnings.featuretoggle

import android.app.Application
import com.launchdarkly.sdk.ContextKind
import com.launchdarkly.sdk.LDContext
import com.launchdarkly.sdk.android.Components
import com.launchdarkly.sdk.android.LDClient
import com.launchdarkly.sdk.android.LDConfig
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class LaunchDarklyFeatureToggleProvider(
    private val clientInfo: FeatureToggleUser,
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val applicationScope: CoroutineScope = GlobalScope,
) {

    companion object {
        private const val CONTEXT_KIND_DEVICE = "device"
        private const val CONTEXT_KIND_APP = "app"

        // keys used for anonymous user
        private const val CONTEXT_KEY_ANONYMOUS_USER = "anonymous_user"

        // Custom attribute keys
        private const val CUSTOM_ATTRIBUTE_DEVICE_OS = "os"
        private const val CUSTOM_ATTRIBUTE_DEVICE_MODEL = "model"
        private const val CUSTOM_ATTRIBUTE_APP_VERSION_NAME = "version_name"
        private const val CUSTOM_ATTRIBUTE_APP_VERSION_CODE = "version_code"
        private const val CUSTOM_ATTRIBUTE_USER_NAME = "name"
        private const val CUSTOM_ATTRIBUTE_USER_EMAIL = "email"
        private const val CUSTOM_ATTRIBUTE_USER_ID = "id"
    }

    fun init(application: Application) {
        val ldConfig = LDConfig.Builder(LDConfig.Builder.AutoEnvAttributes.Enabled)
            .mobileKey("mob-13be3a60-2679-4bfd-a3e3-635b1b376890")
            .generateAnonymousKeys(true)
            .http(Components.httpConfiguration().connectTimeoutMillis(TimeUnit.SECONDS.toMillis(5).toInt()))

            .build()

        val ldContext: LDContext = getLDContext()
        val clientFuture = LDClient.init(application, ldConfig, ldContext)

        applicationScope.launch(ioDispatcher) {
            try {
                clientFuture.get()
            } catch (ex: ExecutionException) {
                println("Failed to initialize LaunchDarkly: ${ex.message}")
                return@launch
            }
        }
    }

    fun isEnabled(flagKey: String, fallback: Boolean): Boolean {
        return try {
            LDClient.get().boolVariation(flagKey, fallback)
        } catch (exception: Exception) {
            println("Failed to get flag value: ${exception.message}")
            fallback
        }
    }

    private fun getLDContext(
    ): LDContext {

        val deviceLDContext = LDContext.builder(ContextKind.of(CUSTOM_ATTRIBUTE_USER_EMAIL), clientInfo.emailAddress)
            .set(CUSTOM_ATTRIBUTE_USER_ID, clientInfo.userServerId)
            .set(CUSTOM_ATTRIBUTE_USER_EMAIL, clientInfo.emailAddress)
            .set(CUSTOM_ATTRIBUTE_USER_NAME, clientInfo.name)
            .build()
        // We use a multi-context to combine all the contexts into one.
        // https://docs.launchdarkly.com/home/contexts/multi-contexts#multi-contexts
        return LDContext.createMulti(
            deviceLDContext,
        )
    }
}