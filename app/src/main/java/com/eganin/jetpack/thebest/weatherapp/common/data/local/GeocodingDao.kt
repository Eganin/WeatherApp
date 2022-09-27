package com.eganin.jetpack.thebest.weatherapp.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.GeocodingEntity

@Dao
interface GeocodingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeocodingInfo(geocodingInfo : GeocodingEntity)

    @Query("DELETE FROM geocodingentity")
    suspend fun clearGeocodingInfo()

    @Query("SELECT * FROM geocodingentity WHERE :cityName == cityName LIMIT 1")
    suspend fun getGeocodingInfo(cityName : String) : GeocodingEntity
}