package org.xtimms.innowise.weather.main

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val mainModule
    get() = module {
        viewModel { MainViewModel(get()) }
    }