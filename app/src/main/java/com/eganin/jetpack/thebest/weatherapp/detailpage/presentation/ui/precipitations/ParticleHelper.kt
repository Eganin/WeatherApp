package com.eganin.jetpack.thebest.weatherapp.detailpage.presentation.ui.precipitations

class ParticleHelper(
    private val parameters : PrecipitationsParameters,
    private val frameWidth : Int,
    private val frameHeight : Int
) {

    private val _particles = mutableListOf<Particle>()
    val particles : List<Particle> = _particles

    //fun generateParticles
}