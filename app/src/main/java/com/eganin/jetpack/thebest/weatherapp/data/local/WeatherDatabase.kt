package com.eganin.jetpack.thebest.weatherapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eganin.jetpack.thebest.weatherapp.data.local.converters.DataStockConverter
import com.eganin.jetpack.thebest.weatherapp.data.local.converters.WeatherDataConverter
import com.eganin.jetpack.thebest.weatherapp.data.local.entities.*

@Database(
    entities = [
        GeocodingEntity::class,
        SunsetSunriseTimeDataEntity::class,
        DataForStockEntity::class,
        WeatherDataEntity::class,
        //WeatherInfoEntity::class
    ],
    version = 1
)
@TypeConverters(DataStockConverter::class,WeatherDataConverter::class)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val geocodingDao : GeocodingDao
    abstract val weatherDataDao : WeatherDataDao
    abstract val sunsetSunriseDao : SunsetSunriseTimeDataDao
    abstract val dataForStockDao : DataForStockDao
    companion object {
        const val NAME_DATABASE = "weather.db"
    }
}