package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.carddetail.WeatherCard
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.listdetail.WeatherForecast
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.search.SearchField
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.search.SearchWidget
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import kotlinx.coroutines.launch

@Composable
fun WeatherDetailPage(viewModel: WeatherViewModel) {

    var isVisibleSearchField by rememberSaveable { mutableStateOf(false) }
    val stateLazyColumn = rememberLazyListState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = viewModel.state.isRefreshing)

    Box(modifier = Modifier.fillMaxSize()) {
        SwipeRefresh(
            state = swipeRefreshState,
            onRefresh = {
                viewModel.onEvent(DetailPageEvent.Refresh)
            }
        ){
            LazyColumn(
                state = stateLazyColumn,
                modifier = Modifier
                    .fillMaxSize()
                    .background(AppTheme.colors.primaryBackground)
            ) {
                if (isVisibleSearchField) {
                    item {
                        rememberCoroutineScope().launch {
                            stateLazyColumn.animateScrollToItem(index = 0)
                        }
                        SearchField(viewModel = viewModel)
                    }
                }
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
                    SearchWidget(
                        modifier = Modifier.padding(
                            top = 16.dp,
                            start = 16.dp,
                            end = 16.dp,
                            bottom = 70.dp
                        )
                    ) {
                        isVisibleSearchField = !isVisibleSearchField
                    }
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