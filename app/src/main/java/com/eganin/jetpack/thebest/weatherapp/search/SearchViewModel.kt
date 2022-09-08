package com.eganin.jetpack.thebest.weatherapp.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.eganin.jetpack.thebest.weatherapp.search.domain.repository.GeocodingRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val geocodingRepository: GeocodingRepository
) : ViewModel() {
    fun loadGeocoding(cityName : String){
        viewModelScope.launch {
            val result = geocodingRepository.getGeoFromCity(cityName=cityName)
            Log.d("EEE",result.toString())
        }
    }
}