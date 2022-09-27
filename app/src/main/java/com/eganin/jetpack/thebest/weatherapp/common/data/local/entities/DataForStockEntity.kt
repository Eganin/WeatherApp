package com.eganin.jetpack.thebest.weatherapp.common.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataForStockEntity(
    val data : List<Int>,
    @PrimaryKey
    val id : Int? = null
)
