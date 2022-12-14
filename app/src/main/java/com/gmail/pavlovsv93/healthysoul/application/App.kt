package com.gmail.pavlovsv93.healthysoul.application

import android.app.Application
import com.gmail.pavlovsv93.healthysoul.BuildConfig
import com.gmail.pavlovsv93.healthysoul.di.appModule
import com.gmail.pavlovsv93.healthysoul.di.dataModule
import com.gmail.pavlovsv93.healthysoul.di.domainModule
import com.gmail.pavlovsv93.healthysoul.di.testsModule
import com.gmail.pavlovsv93.healthysoul.di.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(if (BuildConfig.DEBUG) Level.ERROR else Level.NONE)
            modules(
                domainModule, dataModule, appModule, testsModule, psychologistModule, roomModule
            )
            androidContext(this@App)
        }
    }
}