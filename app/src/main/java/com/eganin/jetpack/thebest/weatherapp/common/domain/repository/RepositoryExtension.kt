package com.eganin.jetpack.thebest.weatherapp.common.domain.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

suspend fun <T> getDataForRepository(data: T?): Resource<T> =
    withContext(Dispatchers.IO) {
        try {
            Resource.Success(
                data = data
            )
        } catch (e: Exception) {
            e.printStackTrace()
            Resource.Error(message = e.message ?: "Unknown error")
        }
    }
