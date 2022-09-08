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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.util.getThemeType
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherDetailPage
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.WeatherViewModel
import com.eganin.jetpack.thebest.weatherapp.search.SearchPage
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppCorners
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.WeatherAppTheme
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: WeatherViewModel by viewModels()
    private lateinit var permissionLauncher: ActivityResultLauncher<Array<String>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissionLauncher = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) {
            viewModel.loadWeatherInfo()
            viewModel.loadDataStock()
        }
        permissionLauncher.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION,
            )
        )
        setContent {
            val currentCornersStyle by remember { mutableStateOf(AppCorners.Rounded) }
            val navController: NavHostController = rememberNavController()
            WeatherAppTheme(
                themeType = getThemeType()
            ) {
                val systemUiController = rememberSystemUiController()
                val statusBarColor = AppTheme.colors.cardBackground
                SideEffect {
                    // setup status bar
                    systemUiController.apply {
                        setSystemBarsColor(color = statusBarColor)
                    }
                }
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = AppTheme.colors.primaryBackground,
                ) {
                    Scaffold(
                        bottomBar = {
                            BottomBar(navController=navController)
                        }
                    ) {
                        NavHost(
                            navController = navController,
                            startDestination = DestinationsPage.Home.name
                        ) {
                            composable(DestinationsPage.Home.name) {
                                WeatherDetailPage(viewModel = viewModel)
                            }
                            composable(DestinationsPage.Search.name) {
                                SearchPage()
                            }
                            composable(DestinationsPage.WeekList.name) {

                            }
                        }
                    }
                }
            }
        }
    }
}

