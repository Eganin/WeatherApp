package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.carddetail.WeatherCard
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.listdetail.WeatherForecast
import com.eganin.jetpack.thebest.weatherapp.search.SearchWidget
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme

@Composable
fun WeatherDetailPage(viewModel: WeatherViewModel) {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(AppTheme.colors.primaryBackground)
        ) {
            item {
                WeatherCard(
                    state = viewModel.state,
                    backgroundColor = AppTheme.colors.cardBackground
                )
            }
            item { Spacer(modifier = Modifier.height(16.dp)) }
            item {
                WeatherForecast(state = viewModel.state)
            }
            item {
                SearchWidget(modifier = Modifier.padding(16.dp)) {
                    Log.d("EEE","CLICK")
                }
            }
        }
        if (viewModel.state.isLoading) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.Center),
                color = Color.White
            )
        }
        viewModel.state.error?.let { error ->
            Text(
                text = error,
                color = AppTheme.colors.errorColor,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }
    }
}