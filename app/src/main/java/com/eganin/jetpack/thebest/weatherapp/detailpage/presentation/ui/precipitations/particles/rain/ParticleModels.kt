package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.rain

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.Scene
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.SceneEntity
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.clouds.Cloud

fun randomXRain(canvasWidth: Int) = (0..canvasWidth).random().toFloat()

fun randomYRain() = (-500..50).random().toFloat()

fun randomZRain() = (1..20).random().toFloat()

fun randomDropLengthRain(z: Float) = z.mapRange(0f to 20f, 10f to 20f)

fun randomFallSpeedRain(z: Float) = z.mapRange(0f to 20f, 4f to 10f)

fun randomDropThicknessRain(z: Float) = z.mapRange(0f to 20f, 1f to 5f)

fun randomGravityOnDropRain(z: Float) = z.mapRange(0f to 20f, 0f to 0.2f)

data class Drop(
    var width: Int=0,
    var height: Int=0,
    var x: Float = randomXRain(canvasWidth = width),
    var y: Float = randomYRain(),
    var z: Float = randomZRain(),
    var length: Float = randomDropLengthRain(z),
    var fallSpeed: Float = randomFallSpeedRain(z),
    var thickness: Float = randomDropThicknessRain(z)
) : SceneEntity {

    private var gravityEffect = randomGravityOnDropRain(z)

    override fun update(scene: Scene) {
        y += fallSpeed
        fallSpeed += gravityEffect
        if (y > height) reset(drop = this)
    }

    private fun reset(drop: Drop) = with(drop) {
        x = randomXRain(canvasWidth = width)
        y = randomYRain()
        z = randomZRain()
        length = randomDropLengthRain(z)
        fallSpeed = randomFallSpeedRain(z)
        thickness = randomDropThicknessRain(z)
        gravityEffect = randomGravityOnDropRain(z)
    }
}

fun Float.mapRange(fromRange: Pair<Float, Float>, toRange: Pair<Float, Float>): Float {
    val (_, maxRange) = fromRange
    val (minMappedRange, maxMappedRange) = toRange
    val rangePercentage = (this / maxRange) * 100
    val mappedValue = (rangePercentage / 100) * (minMappedRange + maxMappedRange)
    return mappedValue
}

fun DrawScope.drawDrop(drop: Drop) {
    val width = size.width
    val height = size.height
    drop.width = width.toInt()
    drop.height = height.toInt()
    drawLine(
        color = Color.Gray,
        start = Offset(x = drop.x, y = drop.y),
        end = Offset(x = drop.x, y = drop.y + drop.length),
        strokeWidth = drop.thickness
    )
}


fun DrawScope.drawCloud(cloud : Cloud,imageBitmap: ImageBitmap){
    val width = size.width
    val height = size.height
    cloud.width = width.toInt()
    cloud.height = height.toInt()
    drawImage(
        image = imageBitmap,
        dstOffset = IntOffset(cloud.x.toInt(), cloud.y.toInt()),
        dstSize = IntSize(150, 100),
    )
}