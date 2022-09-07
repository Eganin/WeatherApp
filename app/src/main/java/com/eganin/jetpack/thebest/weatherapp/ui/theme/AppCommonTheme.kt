package com.eganin.jetpack.thebest.weatherapp.ui.theme

import androidx.compose.runtime.Composable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Dp

data class AppColors(
    val primaryText: Color,
    val primaryBackground: Color,
    val secondaryBackground : Color,
    val secondaryText: Color,
    val tintColor: Color,
    val errorColor: Color,
    val cardBackground : Color,
)

data class AppShape(
    val cornersStyle: Shape,
)

object AppTheme {
    val colors: AppColors
        @Composable
        get() = LocalAppColors.current

    val shapes: AppShape
        @Composable
        get() = LocalAppShape.current
}

enum class AppCorners {
    Flat, Rounded,
}

val LocalAppColors = staticCompositionLocalOf<AppColors> {
    error("No colors providers")
}

val LocalAppShape = staticCompositionLocalOf<AppShape> {
    error("No shapes provided")
}