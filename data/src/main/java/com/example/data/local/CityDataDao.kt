package com.example.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.data.local.entities.CityDataEntity

@Dao
interface CityDataDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCityData(data : CityDataEntity)

    @Query("DELETE FROM citydataentity")
    suspend fun clearCityData()

    @Query("SELECT * FROM citydataentity")
    suspend fun getCityData() : List<CityDataEntity>
}