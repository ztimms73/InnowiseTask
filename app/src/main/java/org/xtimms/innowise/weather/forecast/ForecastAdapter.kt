package org.xtimms.innowise.weather.forecast

import android.annotation.SuppressLint
import android.content.Context
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
    val context: Context,
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
                    "04d" -> {
                        R.drawable.ic_outline_weather_sunny_24
                    }
                    else -> {
                        2
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
        val weathIcon = view.findViewById(R.id.icon) as ImageView
        private val timeTv = view.findViewById(R.id.time) as TextView
        private val weatherInfoTv = view.findViewById(R.id.info) as TextView
        private var tempInfoTV = view.findViewById(R.id.temp) as TextView

        @SuppressLint("SetTextI18n")
        fun bind(icon: Int, temp: Float, name: String, hour: Int) {
            Picasso.get().load(icon).error(R.drawable.ic_outline_weather_sunny_24).into(weathIcon)
            var hourTime = "${hour}.00"
            if (hourTime.length == 4) hourTime = "0$hourTime"
            timeTv.text = hourTime
            tempInfoTV.text = "${temp.roundToInt()}°"
            weatherInfoTv.text = name
        }
    }

    class HeaderViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv = itemView.findViewById(R.id.day) as TextView
    }

    class ErrorViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)


}