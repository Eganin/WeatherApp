package com.eganin.jetpack.thebest.weatherapp.presentation.di

import com.example.domain.location.LocationTracker
import com.example.domain.repository.GeocodingRepository
import com.example.domain.repository.SunsetSunriseTimeRepository
import com.example.domain.repository.WeatherRepository
import com.example.domain.usecase.GetCityFromDb
import com.example.domain.usecase.GetCoordinatesFromCityName
import com.example.domain.usecase.GetDataStock
import com.example.domain.usecase.GetSunsetAndSunriseTimes
import com.example.domain.usecase.GetWeatherInfo
import com.example.domain.usecase.WeatherUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideWeatherUseCases(
        weatherRepository: WeatherRepository,
        geocodingRepository: GeocodingRepository,
        sunriseTimeRepository: SunsetSunriseTimeRepository,
        locationTracker: LocationTracker
    ): WeatherUseCases {
        return WeatherUseCases(
            getCity = GetCityFromDb(repository = weatherRepository),
            getWeatherInfo = GetWeatherInfo(
                locationTracker = locationTracker,
                geocodingRepository = geocodingRepository,
                weatherRepository = weatherRepository
            ),
            getDataStock = GetDataStock(
                locationTracker = locationTracker,
                geocodingRepository = geocodingRepository,
                weatherRepository = weatherRepository
            ),
            getSunsetAndSunriseTimes = GetSunsetAndSunriseTimes(
                locationTracker = locationTracker,
                sunsetSunriseTimeRepository = sunriseTimeRepository,
                geocodingRepository = geocodingRepository
            ),
            getCoordinatesFromCity = GetCoordinatesFromCityName(
                locationTracker = locationTracker,
                geocodingRepository = geocodingRepository,
                weatherRepository = weatherRepository
            )
        )
    }
}