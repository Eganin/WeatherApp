package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.rain

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope

sealed class SceneEntity {
    abstract fun update(scene: SceneRain)
}

fun randomX(canvasWidth: Int) = (0..canvasWidth).random().toFloat()

fun randomY() = (-500..50).random().toFloat()

fun randomZ() = (1..20).random().toFloat()

fun randomDropLength(z: Float) = z.mapRange(0f to 20f, 10f to 20f)

fun randomFallSpeed(z: Float) = z.mapRange(0f to 20f, 4f to 10f)

fun randomDropThickness(z: Float) = z.mapRange(0f to 20f, 1f to 5f)

fun randomGravityOnDrop(z: Float) = z.mapRange(0f to 20f, 0f to 0.2f)

data class Drop(
    var width: Int,
    var height: Int,
    var x: Float = randomX(canvasWidth = width),
    var y: Float = randomY(),
    var z: Float = randomZ(),
    var length: Float = randomDropLength(z),
    var fallSpeed: Float = randomFallSpeed(z),
    var thickness: Float = randomDropThickness(z)
) : SceneEntity() {

    private var gravityEffect = randomGravityOnDrop(z)

    override fun update(scene: SceneRain) {
        y += fallSpeed
        fallSpeed += gravityEffect
        if (y > height) reset(drop = this)
    }

    private fun reset(drop: Drop) = with(drop) {
        x = randomX(canvasWidth = width)
        y = randomY()
        z = randomZ()
        length = randomDropLength(z)
        fallSpeed = randomFallSpeed(z)
        thickness = randomDropThickness(z)
        gravityEffect = randomGravityOnDrop(z)
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