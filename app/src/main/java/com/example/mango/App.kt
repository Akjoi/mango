package com.example.mango

import android.app.Application
import android.content.Context

class App: Application() {
    private var _appComponent: AppComponent? = null

    internal val appComponent: AppComponent
        get() = checkNotNull(_appComponent) {
            "AppComponent isn't initialized"
        }

    override fun onCreate() {
        super.onCreate()
        _appComponent = DaggerAppComponent.builder().preferencesModule(PreferencesModule(this)).build()
    }
}


val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }