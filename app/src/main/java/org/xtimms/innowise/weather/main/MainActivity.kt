package org.xtimms.innowise.weather.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.viewpager.widget.ViewPager
import com.example.innowiseweatherapplication.view.IMainView
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayout
import org.xtimms.innowise.weather.R
import org.xtimms.innowise.weather.base.BaseActivity
import org.xtimms.innowise.weather.base.TabsPagerAdapter
import org.xtimms.innowise.weather.databinding.ActivityMainBinding
import org.xtimms.innowise.weather.model.ForecastRecyclerItemView
import org.xtimms.innowise.weather.model.TodayWeather
import org.xtimms.innowise.weather.presenter.MainPresenter
import org.xtimms.innowise.weather.utils.Utils

class MainActivity : BaseActivity<ActivityMainBinding>(), IMainView {

    private val permissionId = 42

    private lateinit var viewPager: ViewPager
    lateinit var tabLayout: TabLayout

    private lateinit var mFusedLocationClient: FusedLocationProviderClient
    private lateinit var mainPresenter: MainPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(ActivityMainBinding.inflate(layoutInflater))
        init()
        mainPresenter.getLastLocation()
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
        val viewPagerAdapter =
            TabsPagerAdapter(
                supportFragmentManager, todayWeather, items
            )
        viewPager.adapter = viewPagerAdapter
        tabLayout.setupWithViewPager(viewPager)
    }

    override fun init() {
        val utils = Utils(applicationContext)
        mainPresenter = MainPresenter(this, utils)
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        viewPager = findViewById(R.id.pager)
        tabLayout = findViewById(R.id.tabs)

        findViewById<Button>(R.id.retry_button).setOnClickListener {
            mainPresenter.getLastLocation()
        }
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

}