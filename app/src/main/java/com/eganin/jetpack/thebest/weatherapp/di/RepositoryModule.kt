package com.eganin.jetpack.thebest.weatherapp.di

import com.eganin.jetpack.thebest.weatherapp.data.location.DefaultLocationTracker
import com.eganin.jetpack.thebest.weatherapp.data.repository.WeatherRepositoryImpl
import com.eganin.jetpack.thebest.weatherapp.domain.location.LocationTracker
import com.eganin.jetpack.thebest.weatherapp.domain.repository.WeatherRepository
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
}