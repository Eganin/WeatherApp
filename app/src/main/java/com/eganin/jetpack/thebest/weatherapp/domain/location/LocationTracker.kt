package com.eganin.jetpack.thebest.weatherapp.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation() : Location?
}