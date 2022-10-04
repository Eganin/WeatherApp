package com.example.domain.usecase

import com.example.domain.repository.WeatherRepository
import com.example.domain.util.Resource
import kotlinx.coroutines.flow.Flow

class GetCityFromDb(
    private val repository: WeatherRepository
) {

    operator fun invoke() : Flow<Resource<Set<String>>> {
        return repository.getCityNameFromDB()
    }
}