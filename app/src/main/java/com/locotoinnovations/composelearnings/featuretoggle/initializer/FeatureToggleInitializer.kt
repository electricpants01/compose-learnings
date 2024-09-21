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

        // first, we init the impl logic
        FeatureToggleManagerInstance.init(
            featureToggleProvider = FeatureToggleProviderImpl(
                launchDarklyFeatureToggleProvider = launchDarklyFeatureToggleProvider,
            )
        )

        // Then, we init the LaunchDarkly context
        launchDarklyFeatureToggleProvider.init(
            application = application
        )
    }
}