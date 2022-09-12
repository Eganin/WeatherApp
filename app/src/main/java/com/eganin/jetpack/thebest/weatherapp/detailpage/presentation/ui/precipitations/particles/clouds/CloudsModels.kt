package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.clouds

import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.Scene
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.SceneEntity

fun randomXCloud(canvasWidth : Int) = (0..canvasWidth/2).random().toFloat()

fun randomYCloud(canvasHeight : Int) = (0..canvasHeight/2).random().toFloat()

data class Cloud(
    var width: Int=0,
    var height: Int=0,
    var x: Float = randomXCloud(canvasWidth = width),
    var y: Float = randomYCloud(canvasHeight = height),
    var length: Float = 0f,
    var fallSpeed: Float = 0f,
    var thickness: Float = 0f
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