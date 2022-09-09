package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

sealed class DetailPageEvent{
    object Refresh : DetailPageEvent()
    object FirstLoadDataFromCurrentGeolocation : DetailPageEvent()
    object Error : DetailPageEvent()
    object ErrorGeocodingFromCity : DetailPageEvent()
    data class OnSearchQueryChange(val query : String) : DetailPageEvent()
}
