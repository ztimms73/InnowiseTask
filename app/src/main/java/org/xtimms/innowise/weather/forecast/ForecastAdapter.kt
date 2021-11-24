package org.xtimms.innowise.weather.forecast

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView
import kotlin.math.roundToInt

class ForecastAdapter(
    private val list: ArrayList<ForecastRecyclerItemView>
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ForecastRecyclerItemView.HEADER_TYPE -> HeaderViewHolder(parent.inflate(R.layout.header_item))
            ForecastRecyclerItemView.WEATHER_TYPE -> WeatherViewHolder(parent.inflate(R.layout.forecast_item))
            else -> ErrorViewHolder(parent.inflate(R.layout.forecast_item))
        }
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {

        val item = list[position]
        when (item.type) {
            ForecastRecyclerItemView.HEADER_TYPE -> (holder as HeaderViewHolder).tv.text = item.day
            ForecastRecyclerItemView.WEATHER_TYPE -> {
                val icon = when (item.icon) {
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
                (holder as WeatherViewHolder).bind(icon, item.temp, item.name, item.hour)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (list[position].type) {
            ForecastRecyclerItemView.HEADER_TYPE -> ForecastRecyclerItemView.HEADER_TYPE
            ForecastRecyclerItemView.WEATHER_TYPE -> ForecastRecyclerItemView.WEATHER_TYPE
            else -> 0
        }
    }

    private fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false) =
        LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)

    class WeatherViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val weathIcon = view.findViewById(R.id.icon) as ImageView
        private val timeTv = view.findViewById(R.id.time) as TextView
        private val weatherInfoTv = view.findViewById(R.id.info) as TextView
        private var tempInfoTV = view.findViewById(R.id.temp) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(icon: Int, temp: Float, name: String, hour: Int) {
            Picasso.get().load(icon).error(R.drawable.ic_question_24).into(weathIcon)
            var hourTime = "${hour}:00"
            if (hourTime.length == 4) hourTime = "0$hourTime"
            timeTv.text = hourTime
            tempInfoTV.text = "${temp.roundToInt()}°С"
            weatherInfoTv.text = name
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById(R.id.day) as TextView
    }

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}