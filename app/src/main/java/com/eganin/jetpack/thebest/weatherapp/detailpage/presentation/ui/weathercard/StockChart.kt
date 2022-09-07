package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.weathercard

import android.graphics.Paint
import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
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
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.eganin.jetpack.thebest.weatherapp.ui.theme.AppTheme
import com.eganin.jetpack.thebest.weatherapp.ui.theme.Typography
import kotlin.math.roundToInt

@Composable
fun StockChart(info : List<Int>,modifier: Modifier = Modifier) {
    Log.d("EEE",info.toString())
    Card(
        backgroundColor = AppTheme.colors.secondaryBackground,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier
            .padding(16.dp)
            .fillMaxWidth()
            .height(170.dp)
    ) {
        Column {
            Text(
                text = "Temperature",
                style = Typography.h5,
                color = AppTheme.colors.primaryText,
                modifier = Modifier
                    .align(Alignment.Start)
                    .padding(start = 16.dp, top = 16.dp)
            )
            Spacer(modifier = Modifier.height(16.dp))
            LinearTransactionsChart(data = info)
        }

    }
}

@Composable
fun LinearTransactionsChart(
    modifier: Modifier = Modifier,
    data : List<Int>
) {
    val density = LocalDensity.current
    val colorStock = AppTheme.colors.tintColor
    val textPaint = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            // sp to px
            textSize = density.run { 14.sp.toPx() }
        }
    }
    val textPaintLabel = remember(density) {
        Paint().apply {
            color = android.graphics.Color.BLACK
            textAlign = Paint.Align.CENTER
            // sp to px
            textSize = density.run { 9.sp.toPx() }
        }
    }
    Canvas(modifier = modifier.padding(vertical = 16.dp).fillMaxSize()) {
        var xCounter = 30.dp
        var lastX = 0.0f
        var lastY = 0.0f
        val minY = data.minBy { it }.toFloat()
        val maxY= data.maxBy { it }.toFloat()
        data.forEachIndexed { index, value ->
            if(index ==0){
                drawLine(
                    start = Offset(
                        x = 0f,
                        y = minY+16.dp.toPx()
                    ),
                    end = Offset(
                        x = xCounter.toPx(),
                        y = value.toFloat()-16.dp.toPx()
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
                lastX = xCounter.toPx()
                lastY=value.toFloat()-16.dp.toPx()
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White,Color.White),
                        radius = 15f
                    ),
                    radius = 15f,
                    center=Offset(x = xCounter.toPx(),
                        y = value.toFloat()-16.dp.toPx())
                )
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "Morning",
                        xCounter.toPx(),
                        minY+50.dp.toPx(),
                        textPaintLabel
                    )
                    drawText(
                        "${value}C",
                        xCounter.toPx(),
                        minY+70.dp.toPx(),
                        textPaint
                    )
                }

            }else if(index == data.size-1){
                xCounter+=210f.toDp()
                drawLine(
                    start = Offset(
                        x = lastX,
                        y = lastY
                    ),
                    end = Offset(
                        x = xCounter.toPx(),
                        y = value.toFloat()+16.dp.toPx()
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White,Color.White),
                        radius = 15f
                    ),
                    radius = 15f,
                    center=Offset(x = xCounter.toPx(),
                        y = value.toFloat()+16.dp.toPx())
                )
                lastX=xCounter.toPx()
                lastY=value.toFloat()+16.dp.toPx()
                drawContext.canvas.nativeCanvas.apply {
                    drawText(
                        "Night",
                        xCounter.toPx(),
                        minY+50.dp.toPx(),
                        textPaintLabel
                    )
                    drawText(
                        "${value}C",
                        xCounter.toPx(),
                        minY+70.dp.toPx(),
                        textPaint
                    )
                }
                xCounter+=210f.toDp()
                drawLine(
                    start = Offset(
                        x = lastX,
                        y = lastY
                    ),
                    end = Offset(
                        x = xCounter.toPx(),
                        y = maxY
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
            }else {
                xCounter+=210f.toDp()
                drawLine(
                    start = Offset(
                        x = lastX,
                        y = lastY
                    ),
                    end = Offset(
                        x = xCounter.toPx(),
                        y = value.toFloat()+16.dp.toPx()
                    ),
                    color = colorStock,
                    strokeWidth = Stroke.DefaultMiter
                )
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(Color.White,Color.White),
                        radius = 15f
                    ),
                    radius = 15f,
                    center=Offset(x = xCounter.toPx(),
                        y = value.toFloat()+16.dp.toPx())
                )
                lastX=xCounter.toPx()
                lastY=value.toFloat()+16.dp.toPx()

                drawContext.canvas.nativeCanvas.apply {
                    if(index==1){
                        drawText(
                            "Day",
                            xCounter.toPx(),
                            minY+50.dp.toPx(),
                            textPaintLabel
                        )
                    }else{
                        drawText(
                            "Evening",
                            xCounter.toPx(),
                            minY+50.dp.toPx(),
                            textPaintLabel
                        )
                    }
                    drawText(
                        "${value}C",
                        xCounter.toPx(),
                        minY+70.dp.toPx(),
                        textPaint
                    )
                }
            }
        }
    }
}

