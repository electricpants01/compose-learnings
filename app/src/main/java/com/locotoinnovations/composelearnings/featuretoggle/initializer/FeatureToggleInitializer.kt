package com.locotoinnovations.composelearnings.featuretoggle.initializer

import android.app.Application
import com.locotoinnovations.composelearnings.featuretoggle.FeatureToggleManagerInstance
import com.locotoinnovations.composelearnings.featuretoggle.FeatureToggleProviderImpl
import com.locotoinnovations.composelearnings.featuretoggle.FeatureToggleUser
import com.locotoinnovations.composelearnings.featuretoggle.LaunchDarklyFeatureToggleProvider

object FeatureToggleInitializer {

    fun init(
        application: Application,
        clientInfo: FeatureToggleUser,
    ) {

        val launchDarklyFeatureToggleProvider = LaunchDarklyFeatureToggleProvider(
            clientInfo = clientInfo,
        )

        FeatureToggleManagerInstance.init(
            featureToggleProvider = FeatureToggleProviderImpl(
                launchDarklyFeatureToggleProvider = launchDarklyFeatureToggleProvider,
            )
        )

        launchDarklyFeatureToggleProvider.init(
            application = application
        )
    }
}