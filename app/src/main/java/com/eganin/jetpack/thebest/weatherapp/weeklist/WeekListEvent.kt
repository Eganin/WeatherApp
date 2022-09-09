package com.eganin.jetpack.thebest.weatherapp.weeklist

sealed class WeekListEvent{
    data class LoadData(val searchQuery : String ="") : WeekListEvent()
    object Error : WeekListEvent()
}
