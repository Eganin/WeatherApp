package com.example.data.local

import androidx.test.filters.SmallTest
import com.example.data.local.entities.GeocodingEntity
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

@HiltAndroidTest
@ExperimentalCoroutinesApi
@SmallTest
internal class GeocodingDaoTest : DaoTest() {

    private lateinit var dao: GeocodingDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.geocodingDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertData() = runTest {
        val cityName = "Moskow"
        val item = GeocodingEntity(
            latitude = 4.4,
            longitude = 5.34,
            cityName = cityName,
            id = 1
        )

        dao.insertGeocodingInfo(geocodingInfo = item)

        assertThat(dao.getGeocodingInfo(cityName = cityName)).isEqualTo(item)
    }

    @Test
    fun clearData() = runTest {
        val cityName = "Moskow"
        val item = GeocodingEntity(
            latitude = 4.4,
            longitude = 5.34,
            cityName = cityName,
            id = 1
        )

        dao.insertGeocodingInfo(geocodingInfo = item)
        dao.clearGeocodingInfo()

        assertThat(dao.getGeocodingInfo(cityName = cityName)).isNotEqualTo(item)
    }
}