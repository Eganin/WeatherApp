package com.eganin.jetpack.thebest.weatherapp.presentation.detailpage.ui.precipitations.particles

import androidx.compose.runtime.Composable
import androidx.compose.runtime.State

interface SceneEntity {
    fun update(scene: Scene)
}

abstract class Scene{
    abstract fun setupScene()

    abstract fun update()

    @Composable
    abstract fun render(frameState: State<Long>)
}

