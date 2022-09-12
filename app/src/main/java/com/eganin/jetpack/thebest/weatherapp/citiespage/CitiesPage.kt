package com.eganin.jetpack.thebest.weatherapp.citiespage

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel

@Composable
fun CitiesPage(viewModel: WeatherViewModel) {
    val info = viewModel.citiesPageState.info
    Log.d("EEE",info.toString())
}