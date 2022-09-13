package com.eganin.jetpack.thebest.weatherapp.weeklist.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherState
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.listdetail.WeatherForecast
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.weeklist.WeekListEvent
import com.eganin.jetpack.thebest.weatherapp.weeklist.WeekListViewModel

@Composable
fun WeekListPage(
    weekListViewModel: WeekListViewModel,
    weatherViewModel: WeatherViewModel,
    weatherState: WeatherState
) {

    LaunchedEffect(key1 = weatherState) {
        if (weatherState.searchQuery.isNotEmpty()) {
            weekListViewModel.onEvent(
                event = WeekListEvent.LoadData(searchQuery = weatherState.searchQuery)
            )
        } else {
            weekListViewModel.onEvent(event = WeekListEvent.LoadData())
        }
    }
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.primaryBackground)
        ) {
            WeatherForecast(
                state = weatherViewModel.state,
            )
            Spacer(modifier = Modifier.height(22.dp))
            weekListViewModel.state.info?.let { info ->
                LazyColumn {
                    items(info.size) { indexDay ->
                        DailyWeatherDisplay(
                            weatherData = info.get(indexDay),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            indexDay = indexDay
                        )
                    }
                }
            }
        }
    }
}