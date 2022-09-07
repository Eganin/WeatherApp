package com.eganin.jetpack.thebest.weatherapp.detailpage.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation() : Location?
}