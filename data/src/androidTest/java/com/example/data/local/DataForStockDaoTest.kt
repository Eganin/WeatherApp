package com.example.data.local

import androidx.test.filters.SmallTest
import com.example.data.local.entities.DataForStockEntity
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
internal class DataForStockDaoTest : DaoTest() {

    private lateinit var dao: DataForStockDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = database.dataForStockDao
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertDataStockEntity() = runTest {
        val item = DataForStockEntity(
            data = listOf(5, 4, 2, 1),
            id = 1
        )
        dao.insertDataForStock(dataForStockEntity = item)

        assertThat(dao.getDataForStockEntity()).isEqualTo(item)
    }

    @Test
    fun clearDataForStockEntity() = runTest {
        val item = DataForStockEntity(
            data = listOf(5, 4, 2, 1),
            id = 1
        )
        dao.insertDataForStock(dataForStockEntity = item)
        dao.clearDataForStockEntity()

        assertThat(dao.getDataForStockEntity()).isNotEqualTo(item)
    }
}