package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.clouds

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.res.imageResource
import com.eganin.jetpack.thebest.weatherapp.R
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.Scene
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.rain.drawCloud

class SceneCloud(private val particleCount: Int) : Scene() {

    private val clouds = mutableListOf<Cloud>()

    override fun setupScene() {
        clouds.clear()
        repeat(particleCount) { clouds.add(Cloud()) }
    }

    override fun update() {
        for (entity in clouds) {
            entity.update(scene = this)
        }
    }

    @Composable
    override fun render(frameState: State<Long>) {
        val image = ImageBitmap.imageResource(id = R.drawable.cloud)
        Box {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stepFrame = frameState.value
                clouds.forEach {
                    drawCloud(cloud = it, imageBitmap = image)
                }
            }
        }
    }
}