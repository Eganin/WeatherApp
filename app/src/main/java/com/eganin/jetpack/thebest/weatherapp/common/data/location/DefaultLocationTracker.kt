package com.eganin.jetpack.thebest.weatherapp.common.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.eganin.jetpack.thebest.weatherapp.common.domain.location.LocationTracker
import com.google.android.gms.location.*
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.resume

@ExperimentalCoroutinesApi
class DefaultLocationTracker @Inject constructor(
    private val locationClient: FusedLocationProviderClient,
    private val application: Application
) : LocationTracker {

    private val scope = CoroutineScope(Dispatchers.IO)
    private inner class LocationCallbackHelper : LocationCallback() {
        override fun onLocationResult(lr: LocationResult) {
            scope.launch {
                getCurrentLocation()
            }
        }

        override fun onLocationAvailability(la: LocationAvailability) {
            scope.launch {
                getCurrentLocation()
            }
        }
    }

    private var locationRequest = LocationRequest.create()
    private val locationCallbackHelper = LocationCallbackHelper()

    override fun update(){
        locationClient.requestLocationUpdates(locationRequest, locationCallbackHelper, null)
    }

    override suspend fun getCurrentLocation(): Location? {
        val hasAccessFineLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        val hasAccessCoarseLocationPermission = ContextCompat.checkSelfPermission(
            application,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val locationManager =
            application.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val isGpsEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
        val isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        if (!hasAccessCoarseLocationPermission || !hasAccessFineLocationPermission || !isGpsEnabled || !isNetworkEnabled) {
            return null
        }



        return suspendCancellableCoroutine { continuation ->
            locationClient.lastLocation.apply {
                if (isComplete) {
                    if (isSuccessful) {
                        continuation.resume(result)
                    } else {
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    continuation.resume(it)
                }
                addOnFailureListener {
                    continuation.resume(null)
                }
                addOnCanceledListener {
                    continuation.cancel()
                }
            }
        }
    }
}