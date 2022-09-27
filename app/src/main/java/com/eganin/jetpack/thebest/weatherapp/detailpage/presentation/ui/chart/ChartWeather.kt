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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
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
import kotlin.math.abs
import kotlin.math.max

@Composable
fun ChartWeather(info: List<Int>, modifier: Modifier = Modifier) {
    // variable height depending on the maximum value
    val heightCard = if (abs(max(info.max(), info.min())) > 15) 270.dp else 200.dp
    Card(
        backgroundColor = AppTheme.colors.secondaryBackground,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(top = 16.dp)
            .fillMaxWidth()
            .height(heightCard)
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
        val minY = (newData.maxBy { it }.dp * 5).toPx()
        val maxY = (newData.minBy { it }.dp * 5).toPx()
        val coordYLabel = minY + 35.dp.toPx()
        val coordYTemp = minY + 60.dp.toPx()
        val xCounterStep = 220f.toDp()
        val radiusCircle = 5.dp.toPx()
        newData.forEachIndexed { index, value ->
            when (index) {
                0 -> {
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
                    drawInfoCircle(
                        colorCircle = colorCircle,
                        radius=radiusCircle,
                        x=currentX,
                        y = currentY
                    )
                    drawTextInfo(
                        x= currentX,
                        yLabel = coordYLabel,
                        yTemp = coordYTemp,
                        label = morningLabel,
                        textLabelPaint = textPaintLabel,
                        textPaint = textPaint,
                        tempText = "${-value}C"
                    )

                }
                newData.size - 1 -> {
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
                    drawInfoCircle(
                        colorCircle = colorCircle,
                        radius=radiusCircle,
                        x=currentX,
                        y = currentY
                    )
                    drawTextInfo(
                        x= currentX,
                        yLabel = coordYLabel,
                        yTemp = coordYTemp,
                        label = nightLabel,
                        textLabelPaint = textPaintLabel,
                        textPaint = textPaint,
                        tempText = "${-value}C"
                    )
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
                }
                else -> {
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
                    drawInfoCircle(
                        colorCircle = colorCircle,
                        radius=radiusCircle,
                        x=currentX,
                        y = currentY
                    )
                    lastX = currentX
                    lastY = currentY

                    drawContext.canvas.nativeCanvas.apply {
                        if (index == 1) {
                            drawTextInfo(
                                x= currentX,
                                yLabel = coordYLabel,
                                yTemp = coordYTemp,
                                label = dayLabel,
                                textLabelPaint = textPaintLabel,
                                textPaint = textPaint,
                                tempText = "${-value}C"
                            )
                        } else {
                            drawTextInfo(
                                x= currentX,
                                yLabel = coordYLabel,
                                yTemp = coordYTemp,
                                label = eveningLabel,
                                textLabelPaint = textPaintLabel,
                                textPaint = textPaint,
                                tempText = "${-value}C"
                            )
                        }
                    }
                }
            }
        }
    }
}

fun DrawScope.drawTextInfo(
    label: String,
    x: Float,
    yLabel: Float,
    yTemp: Float,
    textLabelPaint: Paint,
    textPaint: Paint,
    tempText : String
) {
    drawContext.canvas.nativeCanvas.apply {
        drawText(
            label,
            x,
            yLabel,
            textLabelPaint
        )
        drawText(
            tempText ,
            x,
            yTemp,
            textPaint
        )
    }
}


fun DrawScope.drawInfoCircle(colorCircle: Color,x:Float,y:Float,radius : Float){
    drawCircle(
        brush = Brush.radialGradient(
            colors = listOf(colorCircle, colorCircle),
            radius = radius
        ),
        radius = radius,
        center = Offset(
            x = x,
            y = y
        )
    )
}