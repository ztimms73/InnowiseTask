package org.xtimms.innowise.weather.api

import io.reactivex.Observable
import org.xtimms.innowise.weather.BuildConfig
import org.xtimms.innowise.weather.model.Weather
import retrofit2.http.GET
import retrofit2.http.Query

interface IWeatherService {

    @GET("data/2.5/forecast?appid=${BuildConfig.API_KEY}")
    fun getWeather(
        @Query("q") city: String,
    ): Observable<Weather>

}