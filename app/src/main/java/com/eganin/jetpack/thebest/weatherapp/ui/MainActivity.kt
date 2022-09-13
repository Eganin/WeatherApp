package com.eganin.jetpack.thebest.weatherapp.ui

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eganin.jetpack.thebest.weatherapp.citiespage.CitiesPage
import com.eganin.jetpack.thebest.weatherapp.citiespage.CitiesViewModel
import com.eganin.jetpack.thebest.weatherapp.common.domain.util.getThemeType
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.DetailPageEvent
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherDetailPage
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.WeatherAppTheme
import com.eganin.jetpack.thebest.weatherapp.weeklist.WeekListViewModel
import com.eganin.jetpack.thebest.weatherapp.weeklist.ui.WeekListPage
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val weatherViewModel: WeatherViewModel by viewModels()
    private val weekListViewModel: WeekListViewModel by viewModels()
    private val citiesViewModel: CitiesViewModel by viewModels()

    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    private fun requestPermissions(){
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            weatherViewModel.onEvent(event = DetailPageEvent.FirstLoadDataFromCurrentGeolocation)
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestPermissions()
        setContent {
            val navController: NavHostController = rememberNavController()
            WeatherAppTheme(
                themeType = getThemeType()
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colors.primaryBackground,
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController = navController)
                        }
                    ) {
                        val tabData = listOf(
                            DestinationsPage.WeekList.name,
                            DestinationsPage.Home.name,
                            DestinationsPage.CityManagement.name,
                        )
                        val pagerState = rememberPagerState(initialPage = 1)
                        HorizontalPager(
                            count = tabData.size,
                            state = pagerState,
                        ) { page ->
                            when (page) {
                                0 -> WeekListPage(
                                    weekListViewModel = weekListViewModel,
                                    weatherViewModel = weatherViewModel,
                                    weatherState = weatherViewModel.state
                                )
                                1 -> WeatherDetailPage(
                                    viewModel = weatherViewModel
                                )
                                2 -> CitiesPage(
                                    viewModel = weatherViewModel,
                                    citiesViewModel = citiesViewModel
                                )

                            }
                        }
                        NavHost(
                            navController = navController,
                            startDestination = DestinationsPage.Home.name
                        ) {
                            composable(DestinationsPage.Home.name) {
                                rememberCoroutineScope().launch {
                                    pagerState.scrollToPage(page = 1)
                                }
                            }
                            composable(DestinationsPage.CityManagement.name) {
                                rememberCoroutineScope().launch {
                                    pagerState.scrollToPage(page = 2)
                                }
                            }
                            composable(DestinationsPage.WeekList.name) {
                                rememberCoroutineScope().launch {
                                    pagerState.scrollToPage(page = 0)
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}


