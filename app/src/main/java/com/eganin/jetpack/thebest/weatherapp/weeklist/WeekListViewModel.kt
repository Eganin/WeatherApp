package com.eganin.jetpack.thebest.weatherapp.weeklist

import androidx.lifecycle.ViewModel
import com.eganin.jetpack.thebest.weatherapp.common.domain.location.LocationTracker
import com.eganin.jetpack.thebest.weatherapp.common.domain.repository.WeatherRepository
import javax.inject.Inject

class WeekListViewModel @Inject constructor(
    private val repository: WeatherRepository,
    private val locationTracker: LocationTracker
) : ViewModel(){
}