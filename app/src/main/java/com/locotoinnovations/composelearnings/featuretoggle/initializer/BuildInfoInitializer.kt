package com.locotoinnovations.composelearnings.featuretoggle.initializer

import android.content.Context
import androidx.startup.Initializer
import com.locotoinnovations.composelearnings.featuretoggle.build.BuildInfo

class BuildInfoInitializer : Initializer<Unit> {

    override fun create(context: Context) {
        println("chris initializo BuildInfo")
        BuildInfo.init(context)
    }

    override fun dependencies(): List<Class<out Initializer<*>>> = emptyList()
}