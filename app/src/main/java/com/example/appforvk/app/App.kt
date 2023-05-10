package com.example.appforvk.app

import android.app.Application
import com.example.appforvk.di.appModule
import com.example.appforvk.di.dataModule
import com.example.appforvk.di.domainModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, dataModule, domainModule))
        }
    }
}