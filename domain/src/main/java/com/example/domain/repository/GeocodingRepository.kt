package com.example.domain.repository

import com.example.domain.models.geocoding.GeocodingData
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

interface GeocodingRepository {
    suspend fun getGeoFromCity(cityName : String,fetchFromRemote: Boolean) : Flow<Resource<GeocodingData>>
}