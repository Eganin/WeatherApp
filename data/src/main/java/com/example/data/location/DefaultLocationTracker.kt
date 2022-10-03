package com.example.data.location

import android.Manifest
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.util.Log
import androidx.core.content.ContextCompat
import com.example.domain.location.LocationTracker
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
                    Log.d("EEE","COMPLETE")
                    if (isSuccessful) {
                        Log.d("EEE","SUCCESSFUL")
                        continuation.resume(result)
                    } else {
                        Log.d("EEE"," NOTSUCCESSFUL")
                        continuation.resume(null)
                    }
                    return@suspendCancellableCoroutine
                }
                addOnSuccessListener {
                    Log.d("EEE","SUCCESS")
                    continuation.resume(it)
                }
                addOnFailureListener {
                    Log.d("EEE","FAIL")
                    continuation.resume(null)
                }
                addOnCanceledListener {
                    Log.d("EEE","CANCEL")
                    continuation.cancel()
                }
            }
        }
    }
}