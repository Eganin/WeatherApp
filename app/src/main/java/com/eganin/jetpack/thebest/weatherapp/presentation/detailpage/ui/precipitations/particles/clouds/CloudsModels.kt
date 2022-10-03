package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.precipitations.particles.clouds

import com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.precipitations.particles.Scene
import com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.precipitations.particles.SceneEntity

fun randomXCloud(canvasWidth : Int) = (0..canvasWidth).random().toFloat()

fun randomYCloud(canvasHeight : Int) = (0..canvasHeight/2).random().toFloat()

data class Cloud(
    var width: Int=0,
    var height: Int=0,
    var x: Float = randomXCloud(canvasWidth = width),
    var y: Float = randomYCloud(canvasHeight = height),
) : SceneEntity {

    override fun update(scene: Scene) {
        x += 0.4f
        if (x > width) reset(drop = this)
    }

    private fun reset(drop: Cloud) = with(drop) {
        x = randomXCloud(canvasWidth = width)
        y = randomYCloud(canvasHeight = height)
    }
}