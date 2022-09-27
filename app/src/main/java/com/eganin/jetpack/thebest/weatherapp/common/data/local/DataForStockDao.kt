package com.eganin.jetpack.thebest.weatherapp.common.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.DataForStockEntity

@Dao
interface DataForStockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDataForStock(dataForStockEntity: DataForStockEntity)

    @Query("DELETE FROM dataforstockentity")
    suspend fun clearDataForStockEntity()

    @Query("SELECT * FROM dataforstockentity")
    suspend fun getDataForStockEntity() : DataForStockEntity
}