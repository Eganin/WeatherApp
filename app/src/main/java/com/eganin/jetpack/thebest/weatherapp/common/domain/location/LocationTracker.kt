package com.eganin.jetpack.thebest.weatherapp.common.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation() : Location?

    fun update()
}