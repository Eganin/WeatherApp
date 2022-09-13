package com.eganin.jetpack.thebest.weatherapp.citiespage

sealed class CitiesPageEvent{
    data class LoadData(val info : List<String>) : CitiesPageEvent()
    object Error : CitiesPageEvent()
}
