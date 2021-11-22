package org.xtimms.innowise.weather

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.xtimms.innowise.weather.main.mainModule
import org.xtimms.innowise.weather.module.settingsModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initKoin()
    }

    private fun initKoin() {
        startKoin {
            androidContext(this@App)
            modules(
                mainModule,
                settingsModule
            )
        }
    }

}