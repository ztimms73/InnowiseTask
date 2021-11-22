package org.xtimms.innowise.weather.utils

import android.content.Context
import android.location.Location
import android.location.LocationManager

private var lctn: Location? = null

fun Context.isLocationEnabled(): Boolean {
    val locationManager: LocationManager =
        getSystemService(Context.LOCATION_SERVICE) as LocationManager
    return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) || locationManager.isProviderEnabled(
        LocationManager.NETWORK_PROVIDER
    )
}

fun isLocationNull(location: Location?) = if (location == null) {
    true
} else {
    lctn = location
    false
}