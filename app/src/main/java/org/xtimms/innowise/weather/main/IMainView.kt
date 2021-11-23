package org.xtimms.innowise.weather.main

import org.xtimms.innowise.weather.model.ForecastRecyclerItemView
import org.xtimms.innowise.weather.model.TodayWeather

interface IMainView {
    fun showError(errorType: String)
    fun showProgress()
    fun hideProgress()
    fun requestPermission()
    fun openTodayWeather(todayWeather: TodayWeather, items: ArrayList<ForecastRecyclerItemView>)
    fun init()
}