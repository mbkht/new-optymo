package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.Route
import com.example.bus_schedules.models.Shape

@Dao
interface RouteDao {
    @Insert
    fun insertAll(vararg routes: Route)

    @Query("SELECT * FROM routes WHERE route_id IN (1, 2, 3, 4, 5, 8, 9)")
    fun getAllRoutes() : List<Route>

    @Query("SELECT DISTINCT shapes.*\n" +
            "FROM trips\n" +
            "INNER JOIN routes ON routes.route_id = trips.route_id\n" +
            "INNER JOIN shapes ON shapes.shape_id = trips.shape_id\n" +
            "WHERE routes.route_id = :routeId\n" +
            "ORDER BY shape_id, shape_pt_sequence")
    fun getShapesById(routeId: Int): List<Shape>

    @Query("SELECT route_color FROM routes WHERE route_id = :routeId")
    fun getRouteColorById(routeId: Int): String
}