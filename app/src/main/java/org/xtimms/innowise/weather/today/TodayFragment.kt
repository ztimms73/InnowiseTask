package org.xtimms.innowise.weather.today

import android.view.LayoutInflater
import android.view.ViewGroup
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseFragment
import org.xtimms.innowise.weather.databinding.FragmentTodayBinding

class TodayFragment: BaseFragment<FragmentTodayBinding>() {

    override fun getTitle(): CharSequence? {
        return context?.getString(R.string.today)
    }

    override fun onInflateView(
        inflater: LayoutInflater,
        container: ViewGroup?
    ) = FragmentTodayBinding.inflate(inflater, container, false)

    companion object {
        fun newInstance() = TodayFragment()
    }

}