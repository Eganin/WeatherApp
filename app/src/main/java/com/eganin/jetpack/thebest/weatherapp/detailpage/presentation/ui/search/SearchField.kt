package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppColors
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme

@Composable
fun SearchField(viewModel: WeatherViewModel) {
    val state = viewModel.state

    val trailingIconView = @Composable {
        IconButton(
            onClick = {}
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "",
                tint = AppTheme.colors.primaryText,
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (state.searchQuery.isNotEmpty()) {
            viewModel.loadGeocoding(cityName = state.searchQuery)
        }
    }

    OutlinedTextField(
        value = state.searchQuery,
        onValueChange = {
            viewModel.loadGeocoding(cityName = it)
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Search...")
        },
        shape = RoundedCornerShape(10.dp),
        trailingIcon = trailingIconView,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = AppTheme.colors.cardBackground,
            cursorColor = AppTheme.colors.tintColor,
            focusedLabelColor = AppTheme.colors.tintColor,
            focusedIndicatorColor = AppTheme.colors.tintColor,
        ),
        maxLines = 1,
        singleLine = true,
    )
}