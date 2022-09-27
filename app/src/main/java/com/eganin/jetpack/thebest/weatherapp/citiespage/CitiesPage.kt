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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.DetailPageEvent
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.dynamicweathersection.DynamicWeatherSection
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography

@Composable
fun CitiesPage(viewModel: WeatherViewModel, citiesViewModel: CitiesViewModel) {
    val info = viewModel.citiesItemList
    val state = citiesViewModel.state
    LaunchedEffect(key1 = Unit) {
        citiesViewModel.onEvent(event = CitiesPageEvent.LoadData(info = info))
    }
    LaunchedEffect(key1 = info) {
        citiesViewModel.onEvent(event = CitiesPageEvent.LoadData(info = info))
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(AppTheme.colors.primaryBackground)
    ) {
        state.info?.let { data ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.primaryBackground)
                    .padding(top = 20.dp, bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
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
                                        event = DetailPageEvent.OnSearchQueryChange(query = it.third)
                                    )
                                },
                            shape = RoundedCornerShape(20.dp)
                        ) {
                            DynamicWeatherSection(
                                info = it.first,
                                sunsetAndSunriseTimeData = it.second,
                                cityName = it.third,
                                isSmallSize = true
                            )
                        }
                    }
                }
            }
        } ?: run {
            WarningText(
                text = stringResource(R.string.error_label_no_data_available),
                modifier = Modifier.align(Alignment.Center)
            )
        }

        state.error?.let { message ->
            WarningText(text = message, modifier = Modifier.align(Alignment.Center))
        }
    }
}

@Composable
fun WarningText(text: String, modifier: Modifier) {
    Text(
        text = text,
        color = AppTheme.colors.errorColor,
        textAlign = TextAlign.Center,
        modifier = modifier,
        style = Typography.h4
    )
}