package com.eganin.jetpack.thebest.weatherapp.citiespage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.DetailPageEvent
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection.DynamicWeatherSection
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography

@Composable
fun CitiesPage(viewModel: WeatherViewModel, citiesViewModel: CitiesViewModel) {
    val info = viewModel.citiesItemList
    val state = citiesViewModel.state
    LaunchedEffect(key1 = info) {
        citiesViewModel.loadDataForCitiesPage(listSearchQuery = info)
    }

    state.info?.let { data ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.primaryBackground)
                .padding(top = 20.dp, bottom = 50.dp)
        ) {
            items(data.size) { index ->
                data[index].let {
                    Card(
                        modifier = Modifier
                            .padding(16.dp)
                            .height(150.dp)
                            .width(450.dp)
                            .clickable {
                                viewModel.onEvent(
                                    event = DetailPageEvent.OnSearchQueryChange(query = info[index])
                                )
                            },
                        shape = RoundedCornerShape(20.dp)
                    ) {
                        DynamicWeatherSection(
                            info = it.first,
                            sunsetAndSunriseTimeData = it.second,
                            cityName = info[index],
                            isSmallSize = true
                        )
                    }
                }
            }
        }
    }
}