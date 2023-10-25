package com.example.bus_schedules

import com.example.bus_schedules.repository.Repository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class DatabaseTest {

    @get:Rule
    val hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var repository: Repository

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun testGetRoutes() = runBlocking {
        // Perform a data retrieval test using your repository
        val routesList = repository.getAllRoutesShapes().toList()

        // Add assertions to verify the data retrieved
        // For example, assert that the list is not empty or contains expected data
        assert(routesList.isNotEmpty())
        for (routes in routesList) {
            assert(routes.first != 0)
            assert(routes.second.isNotEmpty())
        }
    }



    @Test
    fun testGetAllStops() = runBlocking {
        val allStops = repository.getAllStops()
        assert(allStops.isNotEmpty())
    }

}