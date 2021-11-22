package org.xtimms.innowise.weather.module

import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import org.xtimms.innowise.weather.prefs.AppSettings

val settingsModule
    get() = module {

        single { AppSettings(androidContext()) }

    }