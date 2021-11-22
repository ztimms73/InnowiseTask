package org.xtimms.innowise.weather.main

import android.Manifest
import android.os.Bundle
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseActivity
import org.xtimms.innowise.weather.databinding.ActivityMainBinding
import org.xtimms.innowise.weather.forecast.ForecastFragment
import org.xtimms.innowise.weather.prefs.AppSection
import org.xtimms.innowise.weather.today.TodayFragment

class MainActivity : BaseActivity<ActivityMainBinding>(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val permissionId = 42

    private val viewModel by viewModel<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))

        requestPermission()

        supportFragmentManager.findFragmentByTag(TAG_PRIMARY).run {
            openDefaultSection()
        }
    }

    private fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION),
            permissionId
        )
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_today -> {
                viewModel.defaultSection = AppSection.TODAY
                setPrimaryFragment(TodayFragment.newInstance())
            }
            R.id.action_forecast -> {
                viewModel.defaultSection = AppSection.FORECAST
                setPrimaryFragment(ForecastFragment.newInstance())
            }
            else -> return false
        }
        return true
    }

    private fun openDefaultSection() {
        when (viewModel.defaultSection) {
            AppSection.TODAY -> {
                setPrimaryFragment(TodayFragment.newInstance())
            }
            AppSection.FORECAST -> {
                setPrimaryFragment(ForecastFragment.newInstance())
            }
        }
    }

    private fun setPrimaryFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.container, fragment, TAG_PRIMARY)
            .commit()
    }

    private companion object {
        const val TAG_PRIMARY = "primary"
    }

}