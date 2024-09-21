package com.locotoinnovations.composelearnings

import android.app.Application
import android.os.Build
import com.locotoinnovations.composelearnings.featuretoggle.build.BuildConfigDataProvider
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


@HiltAndroidApp
class ComposeLearningApplication: Application(), BuildConfigDataProvider {

    override fun onCreate() {
        super.onCreate()

        // Plant Timber tree based on the build type
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())  // Enable debug logging in debug mode
        }
    }
    override val appName: String = "ComposeLearnings"
    override val appVersionCode: Int = 123
    override val appVersionName: String = BuildConfig.VERSION_NAME
    override val osName: String = "Android"
    override val osVersion: String = Build.VERSION.RELEASE
    override val deviceModel: String = "${Build.MANUFACTURER} ${Build.MODEL}"
    override val isDebug: Boolean = BuildConfig.DEBUG
    override val buildFlavorString: String = "flavour"
}
