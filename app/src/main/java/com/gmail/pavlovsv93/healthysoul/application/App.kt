package com.gmail.pavlovsv93.healthysoul.application

import android.app.Application
import com.gmail.pavlovsv93.healthysoul.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            modules(
                listOf(domainModule, dataModule, appModule, testsModule, psychologistModule)
            )
            androidContext(this@App)
        }
    }
}