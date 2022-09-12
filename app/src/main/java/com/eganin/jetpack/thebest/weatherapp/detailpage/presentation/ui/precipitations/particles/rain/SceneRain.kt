package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.rain

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.particles.Scene

class SceneRain(private val particleCount : Int) : Scene() {

    private val drops = mutableListOf<Drop>()

    override fun setupScene() {
        drops.clear()
        repeat(particleCount * 5) { drops.add(Drop()) }
    }

    override fun update() {
        for (entity in drops) {
            entity.update(scene=this)
        }
    }

    @Composable
    override fun render(frameState: State<Long>) {
        Box {
            Canvas(modifier = Modifier.fillMaxSize()) {
                val stepFrame = frameState.value
                drops.forEach {
                    drawDrop(drop = it)
                }
            }
        }
    }
}