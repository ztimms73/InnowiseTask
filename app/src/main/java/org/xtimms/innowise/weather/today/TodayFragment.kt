package org.xtimms.innowise.weather.today

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.squareup.picasso.Picasso
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseFragment
import org.xtimms.innowise.weather.databinding.FragmentTodayBinding
import org.xtimms.innowise.weather.presenter.TodayPresenter
import kotlin.math.roundToInt

class TodayFragment : BaseFragment<FragmentTodayBinding>(), IToday {

    lateinit var todayWeatherPresenter: TodayPresenter

    override fun getTitle(): CharSequence? {
        return context?.getString(R.string.today)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTodayBinding.inflate(inflater, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        todayWeatherPresenter = TodayPresenter(this)
        init(binding.root)
    }

    override fun init(view: View) {
        binding.share.setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "In ${arguments!!.getString("CITY_NAME")} temperature is ${arguments!!.getFloat("TEMP").roundToInt()
                }°C, ${arguments!!.getString("WEATHER_NAME")}")
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, null))
        }
        todayWeatherPresenter.init(
            arguments!!.getFloat("SPEED"),
            arguments!!.getInt("DEG")
        )
    }

    override fun fillViews(speed: Int, deg: String) {
        Picasso.get().load(arguments!!.getInt("ICON")).error(R.drawable.ic_baseline_error_outline_24).into(binding.icon)
        binding.rain.text = "${arguments!!.getInt("HUMIDITY")}%"
        binding.speed.text = "$speed km/h"
        binding.pressure.text = "${arguments!!.getInt("PRESSURE")} hPa"
        binding.way.text = deg
        binding.tempStatus.text = "${arguments!!.getFloat("TEMP")?.roundToInt()}°C | ${arguments!!.getString("WEATHER_NAME")}"
        binding.city.text = "${arguments!!.getString("CITY_NAME")}, ${arguments!!.getString("COUNTRY_NAME")}"
    }

    companion object {
        fun newInstance() = TodayFragment()
    }

}