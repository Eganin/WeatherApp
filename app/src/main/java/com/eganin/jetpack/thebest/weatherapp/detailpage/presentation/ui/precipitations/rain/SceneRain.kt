package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations.rain

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.ui.Modifier

class SceneRain {

    private var sceneEntity = mutableStateListOf<SceneEntity>()
    private val drops = mutableListOf<Drop>()

    fun setupScene() {
        sceneEntity.clear()
        drops.clear()
        repeat(100 * 5) { drops.add(Drop()) }
        sceneEntity.addAll(drops)
    }

    fun update() {
        for (entity in sceneEntity) {
            entity.update(this)
        }
    }

    @Composable
    fun render(frameState: State<Long>) {
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