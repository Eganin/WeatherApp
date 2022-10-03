package com.example.data.repository

import com.example.domain.util.Resource
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
    } catch (e: Exception) {
        e.printStackTrace()
        emit(Resource.Error(message = "Unknown error.Turn on Gps and restart the application"))
        null
    }

    response?.let {
        emit(Resource.Loading(isLoading = false))
        emit(Resource.Success(data = it))
    }
}