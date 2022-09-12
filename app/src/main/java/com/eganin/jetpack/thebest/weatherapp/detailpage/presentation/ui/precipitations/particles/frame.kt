package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.rain

import androidx.compose.runtime.*
import kotlinx.coroutines.delay

@Composable
fun StepFrame(callback : () -> Unit) : State<Long> {
    val millis = remember { mutableStateOf(0L) }
    LaunchedEffect(key1 = Unit){
        val startTime = withFrameNanos { it }
        while(true){
            withFrameMillis { frameTime ->
                millis.value = frameTime - startTime
            }
            callback()
        }
    }

    return millis
}