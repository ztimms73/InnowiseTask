package org.xtimms.innowise.weather.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.example.innowiseweatherapplication.view.IMainView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.navigation.NavigationBarView
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseActivity
import org.xtimms.innowise.weather.databinding.ActivityMainBinding
import org.xtimms.innowise.weather.forecast.ForecastFragment
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView
import org.xtimms.innowise.weather.model.TodayWeather
import org.xtimms.innowise.weather.prefs.AppSection
import org.xtimms.innowise.weather.presenter.MainPresenter
import org.xtimms.innowise.weather.today.TodayFragment
import org.xtimms.innowise.weather.utils.Utils

class MainActivity : BaseActivity<ActivityMainBinding>(), IMainView,
    NavigationBarView.OnItemSelectedListener {

    private val permissionId = 42
    private val todayArgs = Bundle()
    private val forecastArgs = Bundle()

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private val viewModel by viewModel<MainViewModel>()
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))

        init()
        mainPresenter.getLastLocation()

        binding.bottomNav.setOnItemSelectedListener(this)
        binding.retryButton.setOnClickListener {
            mainPresenter.getLastLocation()
        }

        supportFragmentManager.findFragmentByTag(TAG_PRIMARY).run {
            openDefaultSection()
        }
    }

    override fun showError(errorType: String) {
        binding.errorIcon.visibility = View.VISIBLE
        binding.errorText.apply {
            visibility = View.VISIBLE
            text = errorType
        }
        binding.retryButton.visibility = View.VISIBLE
    }

    override fun showProgress() {
        binding.errorIcon.visibility = View.GONE
        binding.errorText.visibility = View.GONE
        binding.retryButton.visibility = View.GONE
        binding.progress.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progress.visibility = View.GONE
    }

    override fun onDestroy() {
        super.onDestroy()
        mainPresenter.detachView()
    }

    override fun requestPermission() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            permissionId
        )
    }

    override fun openTodayWeather(
        todayWeather: TodayWeather,
        items: ArrayList<ForecastRecyclerItemView>
    ) {
        todayArgs.putInt("HUMIDITY", todayWeather.humidity)
        todayArgs.putFloat("SPEED", todayWeather.speed)
        todayArgs.putInt("DEG", todayWeather.deg)
        todayArgs.putFloat("TEMP", todayWeather.temp)
        todayArgs.putInt("SEA", todayWeather.seaLevel)
        todayArgs.putString("WEATHER_NAME", todayWeather.weatherName)
        todayArgs.putInt("ICON", R.drawable.ic_outline_weather_sunny_24)
        todayArgs.putString("COUNTRY_NAME", todayWeather.countryName)
        todayArgs.putInt("PRESSURE", todayWeather.pressure)
        todayArgs.putString("CITY_NAME", todayWeather.cityName)

        forecastArgs.putParcelableArrayList("ARRAY", items)

        TodayFragment.newInstance().arguments = todayArgs
        ForecastFragment.newInstance().arguments = forecastArgs
    }

    override fun init() {
        val utils = Utils(applicationContext)
        mainPresenter = MainPresenter(this, utils)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == permissionId) {
            if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                mainPresenter.getLastLocation()
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
            }
        }
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
                binding.bottomNav.selectedItemId = R.id.action_today
                setPrimaryFragment(TodayFragment.newInstance())
            }
            AppSection.FORECAST -> {
                binding.bottomNav.selectedItemId = R.id.action_forecast
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