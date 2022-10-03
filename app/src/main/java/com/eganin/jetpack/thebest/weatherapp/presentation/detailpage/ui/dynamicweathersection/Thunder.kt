package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.dynamicweathersection

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import kotlin.random.Random

@Composable
fun Thunder(width: Int, height: Int) {

    val startCoord = Random.nextInt(0, width-200)

    Canvas(
        modifier = Modifier.fillMaxSize(),
        onDraw = {
            val newPoints = listOf(
                Point(x = startCoord.dp.toPx()+100.dp.toPx(), y = 50.dp.toPx()),
                Point(x = startCoord.dp.toPx()+50.dp.toPx(), y = 100.dp.toPx()),
                Point(x = startCoord.dp.toPx()+100.dp.toPx(), y = 150.dp.toPx()),
                Point(x = startCoord.dp.toPx()+50.dp.toPx(), y = height - 20.dp.toPx())
            )
            val path = Path()
            newPoints.forEachIndexed { index, point ->
                if (index == 0) {
                    path.moveTo(point.x, point.y)
                } else {
                    path.lineTo(point.x, point.y)
                }
            }
            drawPath(path, Color.White, style = Stroke(width = 2.dp.toPx()))
        }
    )
}


data class Point(
    val x: Float,
    val y: Float,
)
