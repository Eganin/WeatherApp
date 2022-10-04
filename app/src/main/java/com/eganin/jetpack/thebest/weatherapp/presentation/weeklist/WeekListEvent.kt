package com.eganin.jetpack.thebest.weatherapp.presentation.weeklist

sealed class WeekListEvent{
    data class LoadData(val searchQuery : String ="") : WeekListEvent()
    data class Error(val message : String) : WeekListEvent()
}
