package com.eganin.jetpack.thebest.weatherapp.presentation.di

import com.example.data.repository.WeatherRepositoryImpl
import com.example.domain.repository.WeatherRepository
import com.example.data.repository.GeocodingRepositoryImpl
import com.example.data.repository.SunsetSunriseTimeRepositoryImpl
import com.example.domain.repository.GeocodingRepository
import com.example.domain.repository.SunsetSunriseTimeRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindWeatherRepository(weatherRepositoryImpl: WeatherRepositoryImpl): WeatherRepository

    @Binds
    @Singleton
    abstract fun bindGeocodingRepository(geocodingRepositoryImpl: GeocodingRepositoryImpl): GeocodingRepository

    @Binds
    @Singleton
    abstract fun bindSunsetSunriseTimeRepository(sunsetSunriseTimeRepository: SunsetSunriseTimeRepositoryImpl): SunsetSunriseTimeRepository

}