package com.example.bus_schedules.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.data.models.Trip
import com.example.bus_schedules.data.models.*

@Dao
interface TripDao {
    @Insert
    fun insertAll(vararg trips: Trip)

    @Query("SELECT * FROM trips WHERE trip_id = :tripId")
    fun getTripById(tripId: String): Trip

    @Query("SELECT * FROM trips")
    fun getAllTrips(): List<Trip>

    @Query("DELETE FROM trips")
    fun deleteAll()
}