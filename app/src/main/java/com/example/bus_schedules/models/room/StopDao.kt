package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Query
import com.example.bus_schedules.models.NextTrip
import com.example.bus_schedules.models.Stop

@Dao
interface StopDao {

    @Query(
        "SELECT * FROM stops\n" +
                "WHERE rowid IN (SELECT DISTINCT parent_station FROM stops\n" +
                "INNER JOIN stop_times ON stops.rowid = stop_times.rowid\n" +
                "INNER JOIN trips ON stop_times.trip_id = trips.trip_id\n" +
                "INNER JOIN routes ON trips.route_id = routes.route_id\n" +
                "WHERE routes.route_id IN (1, 2, 3, 4, 5, 8, 9)\n" +
                "ORDER BY stop_name)"
    )
    suspend fun getAllStops(): List<Stop>

    @Query(
        "SELECT DISTINCT  strftime('%H:%M', arrival_time) as arrival_time , stop_times.trip_id\n" +
                "FROM stop_times \n" +
                "INNER JOIN trips ON stop_times.trip_id = trips.trip_id \n" +
                "INNER JOIN calendar_dates ON trips.service_id = calendar_dates.service_id\n" +
                "WHERE TIME('now', 'localtime') <= arrival_time \n" +
                "AND NOT(calendar_dates.date = strftime( '%Y%m%d', 'now') AND exception_type = 2 )\n" +
                "AND trips.service_id = (SELECT\n" +
                "CASE strftime('%w', 'now')\n" +
                "     WHEN 0 THEN 'UN1'\n" +
                "     WHEN 1 THEN 'UN2'\n" +
                "     ELSE 'UN3'\n" +
                "END\n" +
                ")\n" +
                "AND rowid IN (\n" +
                "SELECT DISTINCT rowid FROM stops\n" +
                "WHERE stop_name Like :stopName\n" +
                "AND parent_station IS NOT NULL\n" +
                ")\n" +
                "ORDER BY arrival_time\n" +
                "LIMIT 10\n"
    )
    suspend fun getNextTrips(stopName: String): List<NextTrip>

}