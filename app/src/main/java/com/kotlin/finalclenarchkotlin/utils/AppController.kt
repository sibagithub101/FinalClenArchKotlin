package com.kotlin.finalclenarchkotlin.utils

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber
import timber.log.Timber.Forest.plant


@HiltAndroidApp
class AppController:Application(){
    override fun onCreate() {
        super.onCreate()
        plant(Timber.DebugTree())
    }
}