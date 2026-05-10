package com.example.testapp

import android.app.Application
import com.example.testapp.di.AppComponent
import com.example.testapp.di.DaggerAppComponent

class BaseApplication : Application() {

    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()
    }
}
