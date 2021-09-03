package com.taetae98.qrreader.manager

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SimpleLocationManager @Inject constructor(
    @ApplicationContext
    private val context: Context
) {
    private val manager by lazy { context.getSystemService(LocationManager::class.java) }

    fun getLastLocation(): Location? {
        return if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            when {
                manager.isProviderEnabled(LocationManager.GPS_PROVIDER) -> {
                    manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
                }
                manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER) -> {
                    manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                }
                else -> {
                    null
                }
            }
        } else {
            null
        }
    }

    fun getUpdatedLocation(onLocation: (location: Location) -> Unit) {
        if (context.checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED ||
            context.checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            if (manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0L, 0F) {
                    onLocation.invoke(it)
                }
            } else if (manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0L, 0F) {
                    onLocation.invoke(it)
                }
            }
        } else {
            throw Exception("Permission Denied")
        }
    }
}