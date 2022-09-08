package com.eganin.jetpack.thebest.weatherapp.weeklist.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.listdetail.WeatherForecast
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.weeklist.WeekListViewModel

@Composable
fun WeekListPage(weekListViewModel: WeekListViewModel, weatherViewModel: WeatherViewModel) {

    LaunchedEffect(key1 = Unit){
        weekListViewModel.loadWeatherDataForEveryDay()
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.primaryBackground)
        ) {
            WeatherForecast(
                state = weatherViewModel.state,
                modifier = Modifier.background(AppTheme.colors.cardBackground)
            )
            weekListViewModel.state.info?.let {
                Log.d("EEE",it.get(0)?.get(12).toString())
                Log.d("EEE",it.get(6)?.get(12).toString())
            }
        }
    }
}