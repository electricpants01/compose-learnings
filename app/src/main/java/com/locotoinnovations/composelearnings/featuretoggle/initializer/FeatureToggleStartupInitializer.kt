package com.locotoinnovations.composelearnings.featuretoggle.initializer

import android.app.Application
import android.content.Context
import androidx.startup.Initializer
import com.locotoinnovations.composelearnings.featuretoggle.FeatureToggleUser
import com.locotoinnovations.composelearnings.featuretoggle.build.BuildInfoInitializer

class FeatureToggleStartupInitializer: Initializer<Unit> {

    override fun create(context: Context) {
        println("chris initializo FeatureToggle")
        FeatureToggleInitializer.init(
            application = context as Application,
            clientInfo = FeatureToggleUser(
                userServerId = "123",
                emailAddress = "christian@gmail.com",
                name = "Christian",
            ),
        )
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = listOf(
        BuildInfoInitializer::class.java
    )
}