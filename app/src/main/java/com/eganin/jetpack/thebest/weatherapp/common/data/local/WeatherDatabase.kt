package com.eganin.jetpack.thebest.weatherapp.common.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.eganin.jetpack.thebest.weatherapp.common.data.local.converters.CurrentWeatherDataConverter
import com.eganin.jetpack.thebest.weatherapp.common.data.local.converters.DataStockConverter
import com.eganin.jetpack.thebest.weatherapp.common.data.local.converters.WeatherDataConverter
import com.eganin.jetpack.thebest.weatherapp.common.data.local.entities.*

@Database(
    entities = [
        GeocodingEntity::class,
        SunsetSunriseTimeDataEntity::class,
        DataForStockEntity::class,
        WeatherDataEntity::class,
        WeatherInfoEntity::class
    ],
    version = 1
)
@TypeConverters(
    DataStockConverter::class,
    WeatherDataConverter::class,
    CurrentWeatherDataConverter::class
)
abstract class WeatherDatabase : RoomDatabase() {

    abstract val geocodingDao: GeocodingDao
    abstract val weatherDataDao: WeatherDataDao
    abstract val sunsetSunriseDao: SunsetSunriseTimeDataDao
    abstract val dataForStockDao: DataForStockDao
    abstract val weatherInfoDao : WeatherInfoDao

    companion object {
        const val NAME_DATABASE = "weather.db"
    }
}