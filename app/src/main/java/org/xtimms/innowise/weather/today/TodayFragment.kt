package org.xtimms.innowise.weather.today

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.squareup.picasso.Picasso
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.databinding.FragmentTodayBinding
import org.xtimms.innowise.weather.presenter.TodayPresenter
import kotlin.math.roundToInt

class TodayFragment : Fragment(), IToday {

    lateinit var todayWeatherPresenter: TodayPresenter
    lateinit var todayFragView: View

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        todayFragView = inflater.inflate(R.layout.fragment_today, container, false)
        todayWeatherPresenter = TodayPresenter(this)
        init(todayFragView)
        return todayFragView
    }

    override fun init(view: View) {
        view.findViewById<TextView>(R.id.share).setOnClickListener {
            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(
                    Intent.EXTRA_TEXT,
                    "In ${arguments!!.getString("CITY_NAME")} temperature is ${
                        arguments!!.getFloat("TEMP").roundToInt()
                    } °C, ${arguments!!.getString("WEATHER_NAME")}"
                )
                type = "text/plain"
            }
            startActivity(Intent.createChooser(sendIntent, null))
        }
        todayWeatherPresenter.init(
            arguments!!.getFloat("SPEED"),
            arguments!!.getInt("DEG")
        )
    }

    @SuppressLint("SetTextI18n")
    override fun fillViews(
        speed: Int,
        deg: String
    ) {
        val imageView = todayFragView.findViewById(R.id.icon) as ImageView
        todayFragView.findViewById<TextView>(R.id.rain)?.text =
            "${arguments!!.getInt("HUMIDITY")} %"
        todayFragView.findViewById<TextView>(R.id.speed)?.text = "$speed km/h"
        todayFragView.findViewById<TextView>(R.id.pressure)?.text =
            "${arguments!!.getInt("PRESSURE")} hPa"
        todayFragView.findViewById<TextView>(R.id.way)?.text = deg
        Picasso.get().load(arguments!!.getInt("ICON"))
            .error(R.drawable.ic_question_24).into(imageView)
        todayFragView.findViewById<TextView>(R.id.precipitation)?.text =
            "${arguments!!.getInt("SEA")}"
        todayFragView.findViewById<TextView>(R.id.temp_status)?.text = "${
            arguments!!.getFloat("TEMP").roundToInt()
        } °C | ${arguments!!.getString("WEATHER_NAME")}"
        todayFragView.findViewById<TextView>(R.id.city)?.text =
            "${arguments!!.getString("CITY_NAME")}, ${arguments!!.getString("COUNTRY_NAME")}"
    }

}