package com.eganin.jetpack.thebest.weatherapp.di

import com.eganin.jetpack.thebest.weatherapp.common.data.repository.WeatherRepositoryImpl
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository.GeocodingRepositoryImpl
import com.eganin.jetpack.thebest.weatherapp.detailpage.data.repository.SunsetSunriseTimeRepositoryImpl
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.GeocodingRepository
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.repository.SunsetSunriseTimeRepository
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