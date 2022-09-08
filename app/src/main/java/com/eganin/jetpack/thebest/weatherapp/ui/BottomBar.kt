package com.eganin.jetpack.thebest.weatherapp.ui

import android.util.Log
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme

@Composable
fun BottomBar(navController: NavController) {
    val bottomItems =
        listOf(
            DestinationsPage.WeekList.name,
            DestinationsPage.Home.name,
            DestinationsPage.Search.name,
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
        "Search" -> Icon(
            painter = painterResource(id = R.drawable.ic_baseline_search_24),
            contentDescription = "Search",
            tint = if (isSelected) AppTheme.colors.tintColor else AppTheme.colors.primaryText
        )
        "Home" -> Icon(
            painter = painterResource(id = R.drawable.ic_baseline_home_24),
            contentDescription = "Search",
            tint = if (isSelected) AppTheme.colors.tintColor else AppTheme.colors.primaryText
        )
        "WeekList" -> Icon(
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
    Search
}