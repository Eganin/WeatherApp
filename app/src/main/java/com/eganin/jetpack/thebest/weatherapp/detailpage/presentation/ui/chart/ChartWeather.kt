package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.chart

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography

@Composable
fun ChartWeather(info: List<Int>, modifier: Modifier = Modifier) {
    Card(
        backgroundColor = AppTheme.colors.secondaryBackground,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(180.dp)
    ) {
        Column {
            Text(
                text = stringResource(R.string.temperature_stock_label),
                style = Typography.h5,
                color = AppTheme.colors.primaryText,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearTransactionsChart(
                data = info,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
        }

    }
}

@Composable
fun LinearTransactionsChart(
    modifier: Modifier = Modifier,
    data: List<Int>
) {
    val newData = if (data.filter { it >= 1 }.size == 4) {
        data.map { -it }
    } else {
        data
    }
    val temperatureSign = if (data.filter { it >= 1 }.size == 4) {
        "-"
    } else {
        "+"
    }

    val density = LocalDensity.current
    val colorStock = AppTheme.colors.tintColor
    val colorCircle = AppTheme.colors.primaryText
    val morningLabel = stringResource(R.string.morning_stock_label)
    val nightLabel = stringResource(R.string.night_stock_label)
    val dayLabel = stringResource(R.string.day_stock_label)
    val eveningLabel = stringResource(R.string.evening_stock_label)
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            // sp to px
            textSize = density.run { 18.sp.toPx() }
        }
    }
    val textPaintLabel = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            // sp to px
            textSize = density.run { 14.sp.toPx() }
        }
    }
    val verticalPaddingCanvas = -(newData.minBy { it }.dp * 6)
    Canvas(
        modifier = modifier
            .padding(top = verticalPaddingCanvas)
            .fillMaxSize()
    ) {
        var xCounter = 45.dp
        var lastX = 0.0f
        var lastY = 0.0f
        val minY =(newData.maxBy { it }.dp * 5).toPx()
        val maxY =(newData.minBy { it }.dp * 5).toPx()
        val coordYLabel = minY + 35.dp.toPx()
        val coordYTemp = minY + 60.dp.toPx()
        val xCounterStep = 220f.toDp()
        val radiusCircle = 17f
        newData.forEachIndexed { index, value ->
            if (index == 0) {
                val currentY = (value.dp * 5).toPx()
                val currentX = xCounter.toPx()
                drawLine(
                    start = Offset(
                        x = 0f,
                        y = minY
                    ),
                    end = Offset(
                        x = currentX,
                        y = currentY
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
                lastX = currentX
                lastY = currentY
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(colorCircle, colorCircle),
                        radius = radiusCircle
                    ),
                    radius = radiusCircle,
                    center = Offset(
                        x = currentX,
                        y = currentY
                    )
                )
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        morningLabel,
                        currentX,
                        coordYLabel,
                        textPaintLabel
                    )
                    drawText(
                        "$temperatureSign${-value}C",
                        currentX,
                        coordYTemp,
                        textPaint
                    )
                }

            } else if (index == newData.size - 1) {
                xCounter += xCounterStep
                val currentY = (value.dp * 5).toPx()
                val currentX = xCounter.toPx()
                drawLine(
                    start = Offset(
                        x = lastX,
                        y = lastY
                    ),
                    end = Offset(
                        x = currentX,
                        y = currentY
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(colorCircle, colorCircle),
                        radius = radiusCircle
                    ),
                    radius = radiusCircle,
                    center = Offset(
                        x = currentX,
                        y = currentY
                    )
                )

                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        nightLabel,
                        xCounter.toPx(),
                        coordYLabel,
                        textPaintLabel
                    )
                    drawText(
                        "$temperatureSign${-value}C",
                        xCounter.toPx(),
                        coordYTemp,
                        textPaint
                    )
                }
                drawLine(
                    start = Offset(
                        x = currentX,
                        y = currentY
                    ),
                    end = Offset(
                        x = currentX + xCounterStep.toPx(),
                        y = maxY
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
            } else {
                xCounter += xCounterStep
                val currentY = (value.dp * 5).toPx()
                val currentX = xCounter.toPx()
                drawLine(
                    start = Offset(
                        x = lastX,
                        y = lastY
                    ),
                    end = Offset(
                        x = currentX,
                        y = currentY
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(colorCircle, colorCircle),
                        radius = radiusCircle
                    ),
                    radius = radiusCircle,
                    center = Offset(
                        x = currentX,
                        y = currentY
                    )
                )
                lastX = currentX
                lastY = currentY

                drawContext.canvas.nativeCanvas.apply {
                    if (index == 1) {
                        drawText(
                            dayLabel,
                            currentX,
                            coordYLabel,
                            textPaintLabel
                        )
                    } else {
                        drawText(
                            eveningLabel,
                            currentX,
                            coordYLabel,
                            textPaintLabel
                        )
                    }
                    drawText(
                        "$temperatureSign${-value}C",
                        currentX,
                        coordYTemp,
                        textPaint
                    )
                }
            }
        }
    }
}

