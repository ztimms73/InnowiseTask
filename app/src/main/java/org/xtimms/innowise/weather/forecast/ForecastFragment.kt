package org.xtimms.innowise.weather.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseFragment
import org.xtimms.innowise.weather.databinding.FragmentForecastBinding

class ForecastFragment: BaseFragment<FragmentForecastBinding>() {

    override fun getTitle(): CharSequence? {
        return context?.getString(R.string.forecast)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentForecastBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = ForecastFragment()
    }

}