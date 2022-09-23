package com.eganin.jetpack.thebest.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        DataForStockEntity::class,
        GeocodingEntity::class,
        SunsetSunriseTimeDataEntity::class,
        WeatherDataEntity::class,
        WeatherInfoEntity::class
    ],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    companion object {
        const val NAME_DATABASE = "weather.db"
    }
}