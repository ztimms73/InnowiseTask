package org.xtimms.innowise.weather.model

import io.reactivex.Observable

interface IModel {
  fun getWeather(cityName:String): Observable<Weather>
}