package com.eganin.jetpack.thebest.weatherapp.common.data.repository

import com.eganin.jetpack.thebest.weatherapp.common.domain.util.Resource
import kotlinx.coroutines.flow.FlowCollector
import retrofit2.HttpException
import java.io.IOException

suspend fun <T> FlowCollector<Resource<T>>.bodyForDataLoading(blockResponse: suspend () -> T) {
    emit(Resource.Loading(isLoading = true))
    val response = try {
        blockResponse()
    } catch (e: IOException) {
        e.printStackTrace()
        emit(Resource.Error(message = "Couldn't load data"))
        null
    } catch (e: HttpException) {
        e.printStackTrace()
        emit(Resource.Error(message = "Couldn't load data"))
        null
    }catch(e : Exception){
        e.printStackTrace()
        emit(Resource.Error(message = "Unknown error"))
        null
    }

    response?.let {
        emit(Resource.Success(data = it))

        emit(Resource.Loading(isLoading = false))
    }
}