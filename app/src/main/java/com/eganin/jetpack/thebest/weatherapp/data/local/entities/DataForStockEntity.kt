package com.eganin.jetpack.thebest.weatherapp.data.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class DataForStockEntity(
    val data : List<Int> = emptyList(),
    @PrimaryKey
    val id : Int? = null
)
