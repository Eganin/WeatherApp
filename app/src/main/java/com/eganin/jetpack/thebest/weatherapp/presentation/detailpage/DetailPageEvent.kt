package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage

sealed class DetailPageEvent{
    object Refresh : DetailPageEvent()
    object FirstLoadDataFromCurrentGeolocation : DetailPageEvent()
    data class Error(val message : String) : DetailPageEvent()
    object ErrorGeocodingFromCity : DetailPageEvent()
    data class OnSearchQueryChange(val query : String) : DetailPageEvent()
}
