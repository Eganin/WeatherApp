package com.eganin.jetpack.thebest.weatherapp.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.unit.dp
import com.eganin.jetpack.thebest.weatherapp.detailpage.domain.util.ThemeType

@Composable
fun WeatherAppTheme(
    corners: AppCorners = AppCorners.Rounded,
    themeType : ThemeType,
    content: @Composable () -> Unit) {

    val colors = when(themeType){
        ThemeType.MORNING ->{
            baseMorningPalette
        }
        ThemeType.DAY ->{
            baseDayPalette
        }
        ThemeType.EVENING ->{
            baseEveningPalette
        }
        ThemeType.NIGHT ->{
            baseNightPalette
        }
    }

    val shapes = AppShape(
        cornersStyle = when(corners){
            AppCorners.Flat -> RoundedCornerShape(0.dp)
            AppCorners.Rounded -> RoundedCornerShape(10.dp)
        }
    )

    CompositionLocalProvider (
        LocalAppColors provides  colors,
        LocalAppShape provides shapes,
        content = content
    )
}
