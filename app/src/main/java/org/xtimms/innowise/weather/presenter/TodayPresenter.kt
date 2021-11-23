package org.xtimms.innowise.weather.presenter

import android.content.Intent
import org.xtimms.innowise.weather.today.IToday
import kotlin.math.roundToInt

class TodayPresenter(val view: IToday) : ITodayPresenter {

    override fun sendInfoBtnClicked(string: String): Intent {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, string)
            type = "text/plain"
        }
        return Intent.createChooser(sendIntent, null)
    }

    override fun init(
        speed: Float,
        deg: Int
    ) {
        view.fillViews(
            convertMetresToKiloMetres(speed),
            convertDeg(deg)
        )
    }

    private fun convertMetresToKiloMetres(speed: Float): Int {
        return ((speed * 3600) / 1000).roundToInt()
    }

    private fun convertDeg(deg: Int): String {
        return when (deg) {
            in 1..90 -> "NE"
            90 -> "E"
            in 91..179 -> "SE"
            180 -> "S"
            in 181..269 -> "SW"
            270 -> "W"
            in 271..359 -> "NW"
            360 -> "N"
            else -> ""
        }

    }

}