package com.example.domain.repository

import com.example.domain.models.sunsetsunrisetime.SunsetSunriseTimeData
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface SunsetSunriseTimeRepository {
    fun getSunsetSunriseTime(
        lat: Double,
        lon: Double,
        fetchFromRemote: Boolean
    ): Flow<Resource<SunsetSunriseTimeData>>
}