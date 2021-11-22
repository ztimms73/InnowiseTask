package org.xtimms.innowise.weather.main

import org.xtimms.innowise.weather.base.BaseViewModel
import org.xtimms.innowise.weather.prefs.AppSettings

class MainViewModel(
    settings: AppSettings
) : BaseViewModel() {

    var defaultSection by settings::defaultSection

}