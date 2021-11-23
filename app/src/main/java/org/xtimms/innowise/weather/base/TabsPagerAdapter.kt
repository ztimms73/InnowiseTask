package org.xtimms.innowise.weather.base

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.forecast.ForecastFragment
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView
import org.xtimms.innowise.weather.model.TodayWeather
import org.xtimms.innowise.weather.today.TodayFragment

class TabsPagerAdapter(
    fragmentManager: FragmentManager, private val todayWeatherClass: TodayWeather,
    private val arrayList: ArrayList<ForecastRecyclerItemView>
) :
    FragmentPagerAdapter(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    override fun getItem(position: Int): Fragment {
        when (position) {
            0 -> {
                val fragment = TodayFragment()
                val args = Bundle()
                args.putInt("HUMIDITY", todayWeatherClass.humidity)
                args.putFloat("SPEED", todayWeatherClass.speed)
                args.putInt("DEG", todayWeatherClass.deg)
                args.putFloat("TEMP", todayWeatherClass.temp)
                args.putFloat("FEELS_LIKE", todayWeatherClass.feelsLike)
                args.putString("WEATHER_NAME", todayWeatherClass.weatherName)
                args.putInt("ICON", getResIdIcon(todayWeatherClass.icon))
                args.putString("COUNTRY_NAME", todayWeatherClass.countryName)
                args.putInt("PRESSURE", todayWeatherClass.pressure)
                args.putString("CITY_NAME", todayWeatherClass.cityName)
                fragment.arguments = args
                return fragment
            }
            1 -> {
                val fragment = ForecastFragment()
                val args = Bundle()
                args.putParcelableArrayList("ARRAY", arrayList)
                fragment.arguments = args
                return fragment
            }
        }
        return TodayFragment()
    }

    override fun getPageTitle(position: Int): CharSequence {
        when (position) {
            0 -> return "Today"
            1 -> return "Forecast"
        }
        return "Error"
    }

    override fun getCount(): Int {
        return 2
    }

    // https://openweathermap.org/weather-conditions
    private fun getResIdIcon(name: String) = when (name) {
        "01d" -> {
            R.drawable.ic_outline_weather_sunny_24
        }
        "01n" -> {
            R.drawable.ic_outline_nightlight_24
        }
        "02d" -> {
            R.drawable.ic_outline_weather_sunny_24
        }
        "02n" -> {
            R.drawable.ic_outline_nightlight_24
        }
        "03d" -> {
            R.drawable.ic_partly_cloudy_24
        }
        "03n" -> {
            R.drawable.ic_partly_cloudy_night_24
        }
        "04d" -> {
            R.drawable.ic_outline_cloud_24
        }
        "04n" -> {
            R.drawable.ic_outline_cloud_24
        }
        "09d" -> {
            R.drawable.ic_shower_rain_24
        }
        "09n" -> {
            R.drawable.ic_shower_rain_24
        }
        "10d" -> {
            R.drawable.ic_rain_24
        }
        "10n" -> {
            R.drawable.ic_rain_24
        }
        "11d" -> {
            R.drawable.ic_thunderstorm_24
        }
        "11n" -> {
            R.drawable.ic_thunderstorm_24
        }
        "13d" -> {
            R.drawable.ic_snowflake_24
        }
        "13n" -> {
            R.drawable.ic_snowflake_24
        }
        "50d" -> {
            R.drawable.ic_fog_24
        }
        "50n" -> {
            R.drawable.ic_fog_24
        }
        else -> {
            R.drawable.ic_question_24
        }
    }

}