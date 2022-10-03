package com.example.domain.location

import android.location.Location

interface LocationTracker {
    suspend fun getCurrentLocation() : Location?

    fun update()
}