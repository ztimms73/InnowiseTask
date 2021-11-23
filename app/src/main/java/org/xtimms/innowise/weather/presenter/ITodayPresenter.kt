package org.xtimms.innowise.weather.presenter

import android.content.Intent

interface ITodayPresenter {
    fun sendInfoBtnClicked(string: String): Intent
    fun init(speed: Float, deg: Int)
}