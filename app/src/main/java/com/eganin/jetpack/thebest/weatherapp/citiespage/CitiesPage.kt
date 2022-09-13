package com.eganin.jetpack.thebest.weatherapp.citiespage

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel

@Composable
fun CitiesPage(viewModel: WeatherViewModel , citiesViewModel: CitiesViewModel) {
    val info = viewModel.citiesItemList
    LaunchedEffect(key1 = info){
        citiesViewModel.loadDataForCitiesPage(listSearchQuery = info)
    }
}