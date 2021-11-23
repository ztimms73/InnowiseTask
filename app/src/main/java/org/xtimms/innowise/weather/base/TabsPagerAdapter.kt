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
                val bundle = Bundle()

                bundle.putInt("HUMIDITY", todayWeatherClass.humidity)
                bundle.putFloat("SPEED", todayWeatherClass.speed)
                bundle.putInt("DEG", todayWeatherClass.deg)
                bundle.putFloat("TEMP", todayWeatherClass.temp)
                bundle.putInt("SEA", todayWeatherClass.seaLevel)
                bundle.putString("WEATHER_NAME", todayWeatherClass.weatherName)
                bundle.putInt("ICON", getResIdIcon(todayWeatherClass.icon))
                bundle.putString("COUNTRY_NAME", todayWeatherClass.countryName)
                bundle.putInt("PRESSURE", todayWeatherClass.pressure)
                bundle.putString("CITY_NAME", todayWeatherClass.cityName)

                fragment.arguments = bundle

                return fragment
            }
            1 -> {
                val fragment = ForecastFragment()
                val bundle = Bundle()
                bundle.putParcelableArrayList("ARRAY", arrayList)
                fragment.arguments = bundle
                return fragment
            }
        }
        return TodayFragment()
    }

    override fun getPageTitle(position: Int): CharSequence? {
        when (position) {
            0 -> return "Today"
            1 -> return "Forecast"
        }
        return "Error"
    }

    override fun getCount(): Int {
        return 2
    }

    private fun getResIdIcon(name: String) = when (name) {
        "04d" -> {
            R.drawable.ic_outline_weather_sunny_24
        }
        else -> {
            2
        }
    }

}