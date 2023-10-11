package com.example.bus_schedules.repository

import android.graphics.Color
import com.example.bus_schedules.models.*
import com.example.bus_schedules.models.room.RouteDao
import com.example.bus_schedules.models.room.StopDao
import com.example.bus_schedules.models.room.TripDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import javax.inject.Inject
import kotlin.math.abs

class Repository @Inject constructor(
    private val routeDao: RouteDao,
    private val stopDao: StopDao,
    private val tripDao: TripDao
){

    private fun getMinLeft(arrivalTimeString: String): Int {
        val arrivalTime = LocalTime.parse(arrivalTimeString, DateTimeFormatter.ofPattern("HH:mm"))
        val currentTime = LocalTime.now()
        val diff = abs(ChronoUnit.MINUTES.between(arrivalTime, currentTime)).toInt()
        return if (abs(diff) > 30.0) {
            -1
        } else {
            diff
        }
    }

    fun getNextTrips(stopName: String): List<Schedule> {
        return stopDao.getNextTrips(stopName).map {
            val trip = tripDao.getTripById(it.tripId)
            val routeColor = Color.parseColor("#" + routeDao.getRouteColorById(trip.routeId))

            // Time left and arrival time
            val minLeft = getMinLeft(it.arrivalTime)

            Schedule(trip.routeId, routeColor, trip.tripHeadsign!!, it.arrivalTime, minLeft)
        }
    }

    fun getAllStops(): List<Stop>{
        return stopDao.getAllStops()
    }

    suspend fun getAllRoutesShapes(): Flow<Pair<Int, List<Shape>>> {
        return flow {
            routeDao.getAllRoutes().forEach { route ->
                val color = Color.parseColor("#" + route.routeColor)
                val shapes = routeDao.getShapesById(route.routeId.toInt())
                emit(Pair(color, shapes))
            }
        }
    }
}
