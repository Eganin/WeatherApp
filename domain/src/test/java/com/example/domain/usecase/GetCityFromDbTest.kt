package com.example.domain.usecase

import com.example.domain.repository.WeatherRepository
import com.example.domain.util.Resource
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import org.mockito.Mockito
import org.mockito.kotlin.mock


@ExperimentalCoroutinesApi
internal class GetCityFromDbTest {

    private val weatherRepository = mock<WeatherRepository>()
    private val useCase = GetCityFromDb(repository = weatherRepository)

    @After
    fun teardown() = Mockito.reset(weatherRepository)

    @Test
    fun `returns data`() = runTest {
        val testData =
            flow<Resource<Set<String>>> { Resource.Success(data = setOf("Moskow", "London")) }

        Mockito.`when`(weatherRepository.getCityNameFromDB()).thenReturn(
            testData
        )

        val actual = useCase()

        assertThat(actual).isEqualTo(testData)
    }
}