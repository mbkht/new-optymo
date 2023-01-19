package com.example.bus_schedules

import android.graphics.Color
import com.example.bus_schedules.models.*
import com.example.bus_schedules.models.room.RouteDao
import com.example.bus_schedules.models.room.StopDao
import com.example.bus_schedules.models.room.TripDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class Repository @Inject constructor(
    private val routeDao: RouteDao,
    private val stopDao: StopDao,
    private val tripDao: TripDao
){

    suspend fun getNextTrips(stopName: String): List<Schedule> {
        return stopDao.getNextTrips(stopName).map {
            val trip = tripDao.getTripById(it.tripId)
            val routeColor = routeDao.getRouteColorById(trip.routeId)
            Schedule(it.arrivalTime, trip, routeColor)
        }
    }

    suspend fun getAllStops(): List<Stop>{
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
