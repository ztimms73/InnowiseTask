package org.xtimms.innowise.weather.presenter

interface IMainPresenter {

    fun getData(cityName: String)
    fun detachView()
    fun getLastLocation()

}