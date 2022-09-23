package com.eganin.jetpack.thebest.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.eganin.jetpack.thebest.weatherapp.data.local.entities.*

@Database(
    entities = [
        GeocodingEntity::class,
        //SunsetSunriseTimeDataEntity::class,

        //DataForStockEntity::class,
        //WeatherDataEntity::class,
        //WeatherInfoEntity::class
    ],
    version = 1
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val geocodingDao : GeocodingDao
    companion object {
        const val NAME_DATABASE = "weather.db"
    }
}