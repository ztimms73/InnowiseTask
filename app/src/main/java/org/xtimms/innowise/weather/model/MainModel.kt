package org.xtimms.innowise.weather.model

import io.reactivex.Observable
import org.xtimms.innowise.weather.api.OWMRetrofitApi

class MainModel : IModel {

    override fun getWeather(cityName: String): Observable<Weather> {
        return OWMRetrofitApi.provideWeather(cityName)
    }

}