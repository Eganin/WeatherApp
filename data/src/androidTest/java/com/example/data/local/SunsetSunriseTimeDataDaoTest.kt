package com.example.data.local

import androidx.test.filters.SmallTest
import com.example.data.local.entities.SunsetSunriseTimeDataEntity
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
class SunsetSunriseTimeDataDaoTest : DaoTest() {

    private lateinit var dao: SunsetSunriseTimeDataDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.sunsetSunriseDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertData() = runTest {
        val item = SunsetSunriseTimeDataEntity(
            id = 1,
            sunsetHour = 6,
            sunriseHour = 18
        )

        dao.insertSunsetAndSunriseInfo(sunsetSunriseEntity = item)

        assertThat(dao.getSunsetAndSunriseInfo()).isEqualTo(item)
    }

    @Test
    fun clearData() = runTest {
        val item = SunsetSunriseTimeDataEntity(
            id = 1,
            sunsetHour = 6,
            sunriseHour = 18
        )

        dao.insertSunsetAndSunriseInfo(sunsetSunriseEntity = item)
        dao.clearSunsetAndSunriseInfo()

        assertThat(dao.getSunsetAndSunriseInfo()).isNotEqualTo(item)
    }
}