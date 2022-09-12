package com.eganin.jetpack.thebest.weatherapp.citiespage

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel

@Composable
fun CitiesPage(viewModel: CitiesViewModel) {
    LaunchedEffect(key1 = Unit){
        viewModel.loadData()
    }
}