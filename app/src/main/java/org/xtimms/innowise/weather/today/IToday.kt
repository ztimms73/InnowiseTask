package org.xtimms.innowise.weather.today

import android.view.View

interface IToday {
    fun init(view: View)
    fun fillViews(
        speed: Int,
        deg: String
    )
}