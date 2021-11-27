package org.xtimms.innowise.weather.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import org.xtimms.innowise.weather.model.Weather
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OWMRetrofitApi {

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/")
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val api = retrofit.create(IWeatherService::class.java)

    fun provideWeather(cityName: String): Observable<Weather> {
        return api.getWeather(cityName)
    }

}