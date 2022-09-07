package com.eganin.jetpack.thebest.weatherapp.di

import com.eganin.jetpack.thebest.weatherapp.detailpage.data.location.DefaultLocationTracker
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.location.LocationTracker
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class LocationModule {

    @Binds
    @Singleton
    abstract fun bindLocationTracker(defaultLocationTracker: DefaultLocationTracker): LocationTracker
}