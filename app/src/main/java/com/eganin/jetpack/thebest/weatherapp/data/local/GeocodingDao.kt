package com.eganin.jetpack.thebest.weatherapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.weatherapp.data.local.entities.GeocodingEntity

@Dao
interface GeocodingDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeocodingInfo(geocodingInfo : GeocodingEntity)

    @Query("DELETE FROM geocodingentity")
    suspend fun clearGeocodingInfo()

    @Query("SELECT * FROM geocodingentity")
    suspend fun getGeocodingInfo() : GeocodingEntity
}