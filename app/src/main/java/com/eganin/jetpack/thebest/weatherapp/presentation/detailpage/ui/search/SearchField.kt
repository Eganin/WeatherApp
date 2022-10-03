package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.search

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.DetailPageEvent
import com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.Typography

@Composable
fun SearchField(viewModel: WeatherViewModel) {
    val state = viewModel.state

    val trailingIconView = @Composable {
        IconButton(onClick = {
            if (state.searchQuery.isNotEmpty()) {
                viewModel.onEvent(event = DetailPageEvent.OnSearchQueryChange(query = state.searchQuery))
            }
        }) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = "",
                tint = AppTheme.colors.primaryText,
            )
        }
    }

    LaunchedEffect(key1 = Unit) {
        if (state.searchQuery.isNotEmpty()) {
            viewModel.onEvent(event = DetailPageEvent.OnSearchQueryChange(query = state.searchQuery))
        }
    }

    OutlinedTextField(
        value = state.searchQuery,
        onValueChange = { query ->
            viewModel.onEvent(event = DetailPageEvent.OnSearchQueryChange(query = query))
        },
        modifier = Modifier
            .padding(16.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(text = "Search...", style = Typography.h5, color = AppTheme.colors.secondaryText)
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