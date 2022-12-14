package com.eganin.jetpack.thebest.weatherapp.presentation.ui

import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.presentation.ui.theme.AppTheme

@Composable
fun BottomBar(navController: NavController) {
    val bottomItems =
        listOf(
            DestinationsPage.WeekList.name,
            DestinationsPage.Home.name,
            DestinationsPage.CityManagement.name,
        )

    BottomNavigation(backgroundColor = AppTheme.colors.secondaryBackground) {
        bottomItems.forEach { screen ->
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val isSelected = navBackStackEntry?.destination?.hierarchy
                ?.any { it.route == screen } == true
            BottomNavigationItem(
                selected = false,
                onClick = { navController.navigate(screen) },
                icon = {
                    getIconForBottomBar(screen = screen, isSelected = isSelected)
                })
        }
    }
}

@Composable
private fun getIconForBottomBar(screen: String, isSelected: Boolean = false) {
    return when (screen) {
        stringResource(R.string.city_management_destination) -> Icon(
            painter = painterResource(id = R.drawable.ic_baseline_location_city_24),
            contentDescription = "Search",
            tint = if (isSelected) AppTheme.colors.tintColor else AppTheme.colors.primaryText
        )
        stringResource(R.string.home_destination) -> Icon(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "Search",
            tint = if (isSelected) AppTheme.colors.tintColor else AppTheme.colors.primaryText
        )
        stringResource(R.string.week_list_destibation) -> Icon(
            painter = painterResource(id = R.drawable.ic_baseline_view_list_24),
            contentDescription = "Search",
            tint = if (isSelected) AppTheme.colors.tintColor else AppTheme.colors.primaryText
        )
        else -> {}
    }
}

enum class DestinationsPage {
    WeekList,
    Home,
    CityManagement
}