package com.example.data.local

import androidx.test.filters.SmallTest
import com.example.data.local.entities.CurrentWeatherDataEntity
import com.example.data.local.entities.WeatherInfoEntity
import com.example.domain.models.weather.WeatherState
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test


@ExperimentalCoroutinesApi
@SmallTest
@HiltAndroidTest
internal class WeatherInfoDaoTest : DaoTest() {

    private lateinit var dao: WeatherInfoDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.weatherInfoDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertWeatherInfo() = runTest {
        val item = WeatherInfoEntity(
            id=1,
            weatherDataPerDay = mapOf(
                0 to listOf(
                    CurrentWeatherDataEntity(
                        time = "18:00",
                        temperatureCelsius = 5.0,
                        pressure = 90.0,
                        windSpeed = 5.0,
                        humidity = 5.4,
                        weatherCode = 1,
                        state = WeatherState.CLEAR_SKY,
                    )
                )
            ),
            currentWeatherData = CurrentWeatherDataEntity(
                time = "18:00",
                temperatureCelsius = 5.0,
                pressure = 90.0,
                windSpeed = 5.0,
                humidity = 5.4,
                weatherCode = 1,
                state = WeatherState.CLEAR_SKY,
            )
        )

        dao.insertWeatherInfo(weatherInfoEntity = item)

        assertThat(dao.getWeatherInfo()).isEqualTo(item)
    }

    @Test
    fun clearAllWeatherInfo() = runTest {
        val item = WeatherInfoEntity(
            id=1,
            weatherDataPerDay = mapOf(
                0 to listOf(
                    CurrentWeatherDataEntity(
                        time = "18:00",
                        temperatureCelsius = 5.0,
                        pressure = 90.0,
                        windSpeed = 5.0,
                        humidity = 5.4,
                        weatherCode = 1,
                        state = WeatherState.CLEAR_SKY,
                    )
                )
            ),
            currentWeatherData = CurrentWeatherDataEntity(
                time = "18:00",
                temperatureCelsius = 5.0,
                pressure = 90.0,
                windSpeed = 5.0,
                humidity = 5.4,
                weatherCode = 1,
                state = WeatherState.CLEAR_SKY,
            )
        )

        dao.insertWeatherInfo(weatherInfoEntity = item)
        dao.clearWeatherInfo()

        assertThat(dao.getWeatherInfo()).isNull()
    }
}