package com.example.data.local

import androidx.test.filters.SmallTest
import com.example.data.local.entities.WeatherDataEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@SmallTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
internal class WeatherDataDaoTest : DaoTest() {

    private lateinit var dao: WeatherDataDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.weatherDataDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertData() = runTest {
        val item = WeatherDataEntity(
            time = listOf("18:00"),
            temperatures = listOf(6.0),
            weatherCodes = listOf(1),
            pressures = listOf(5.4),
            winsSpeeds = listOf(2.3),
            humidities = listOf(4.88),
            id = 1
        )

        dao.insertWeatherData(weatherData = item)

        assertThat(dao.getWeatherDataInfo()).isEqualTo(item)
    }

    @Test
    fun clearData() = runTest {
        val item = WeatherDataEntity(
            time = listOf("18:00"),
            temperatures = listOf(6.0),
            weatherCodes = listOf(1),
            pressures = listOf(5.4),
            winsSpeeds = listOf(2.3),
            humidities = listOf(4.88),
            id = 1
        )

        dao.insertWeatherData(weatherData = item)
        dao.clearWeatherData()

        assertThat(dao.getWeatherDataInfo()).isNotEqualTo(item)
    }
}