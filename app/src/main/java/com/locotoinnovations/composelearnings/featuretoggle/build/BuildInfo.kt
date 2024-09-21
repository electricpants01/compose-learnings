package com.locotoinnovations.composelearnings.featuretoggle.build

import android.content.Context
import android.content.pm.ApplicationInfo

object BuildInfo {
    @JvmStatic
    var isDebug: Boolean = false
        private set

    @JvmStatic
    var isDebuggable: Boolean = false
        private set

    fun init(context: Context) {
        val buildConfigProvider =
            context.applicationContext as BuildConfigDataProvider // Crash if the application isn't providing this
        this.isDebug = buildConfigProvider.isDebug
        this.isDebuggable = (context.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE) != 0
    }
}

interface BuildConfigDataProvider {
    val appName: String
    val appVersionCode: Int
    val appVersionName: String
    val osName: String
    val osVersion: String
    val deviceModel: String
    val isDebug: Boolean
    val buildFlavorString: String
}