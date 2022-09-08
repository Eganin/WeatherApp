package com.eganin.jetpack.thebest.weatherapp.common.domain.util

import java.time.LocalDateTime

fun getThemeType(): ThemeType {
    val hour = LocalDateTime.now().hour
    val type = when (hour) {
        in 6..11 -> ThemeType.MORNING
        in 12..17 -> ThemeType.DAY
        in 18..23 -> ThemeType.EVENING
        else -> ThemeType.NIGHT
    }
    return type
}
enum class ThemeType{
    MORNING,DAY,EVENING,NIGHT
}