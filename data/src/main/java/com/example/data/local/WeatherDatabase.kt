package com.example.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.data.local.converters.CurrentWeatherDataConverter
import com.example.data.local.converters.DataStockConverter
import com.example.data.local.converters.WeatherDataConverter
import com.example.data.local.entities.*

@Database(
    entities = [
        GeocodingEntity::class,
        SunsetSunriseTimeDataEntity::class,
        DataForStockEntity::class,
        WeatherDataEntity::class,
        WeatherInfoEntity::class,
        CityDataEntity::class
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
    abstract val weatherInfoDao: WeatherInfoDao
    abstract val cityDataDao: CityDataDao

    companion object {
        const val NAME_DATABASE = "weather.db"
    }
}