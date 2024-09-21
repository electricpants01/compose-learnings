package com.locotoinnovations.composelearnings.featuretoggle.build

import android.content.Context
import androidx.startup.Initializer

class BuildInfoInitializer : Initializer<Unit> {
    override fun create(context: Context) {
        BuildInfo.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}