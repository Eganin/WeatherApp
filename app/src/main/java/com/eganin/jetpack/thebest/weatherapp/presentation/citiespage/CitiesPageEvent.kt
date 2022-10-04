package com.eganin.jetpack.thebest.weatherapp.presentation.citiespage

sealed class CitiesPageEvent{
    data class LoadData(val info : List<String>?) : CitiesPageEvent()
    data class Error(val message : String) : CitiesPageEvent()
}
