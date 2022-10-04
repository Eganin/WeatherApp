package com.example.data.local

import androidx.test.filters.SmallTest
import com.example.data.local.entities.CityDataEntity
import com.example.data.local.entities.CurrentWeatherDataEntity
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
internal class CityDataDaoTest: DaoTest() {

    private lateinit var dao: CityDataDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.cityDataDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertCityDataEntity() = runTest {
        val item = CityDataEntity(
            currentWeatherDataEntity = CurrentWeatherDataEntity(
                time = "18:00",
                temperatureCelsius = 5.0,
                pressure = 90.0,
                windSpeed = 5.0,
                humidity = 5.4,
                weatherCode = 1,
                state = WeatherState.CLEAR_SKY,
            ),
            id=1,
            sunriseHour = 6,
            sunsetHour = 18,
            cityName = "Moskow"
        )

        dao.insertCityData(data = item)

        val allItems = dao.getCityData()

        assertThat(allItems).contains(item)
    }

    @Test
    fun clearAllCityDataEntity() = runTest {
        val item = CityDataEntity(
            currentWeatherDataEntity = CurrentWeatherDataEntity(
                time = "18:00",
                temperatureCelsius = 5.0,
                pressure = 90.0,
                windSpeed = 5.0,
                humidity = 5.4,
                weatherCode = 1,
                state = WeatherState.CLEAR_SKY,
            ),
            id=1,
            sunriseHour = 6,
            sunsetHour = 18,
            cityName = "Moskow"
        )
        val item2 = CityDataEntity(
            currentWeatherDataEntity = CurrentWeatherDataEntity(
                time = "18:00",
                temperatureCelsius = 5.0,
                pressure = 90.0,
                windSpeed = 5.0,
                humidity = 5.4,
                weatherCode = 1,
                state = WeatherState.CLEAR_SKY,
            ),
            id=2,
            sunriseHour = 6,
            sunsetHour = 18,
            cityName = "Moskow"
        )

        dao.insertCityData(data=item)
        dao.insertCityData(data=item2)
        dao.clearCityData()

        assertThat(dao.getCityData()).isEmpty()
    }
}