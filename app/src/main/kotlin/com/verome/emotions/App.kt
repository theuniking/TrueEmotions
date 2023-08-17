package com.verome.emotions

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import me.nemiron.hyperion.chucker.BuildConfig
import timber.log.Timber

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()

        initLogger()
    }

    private fun initLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

}