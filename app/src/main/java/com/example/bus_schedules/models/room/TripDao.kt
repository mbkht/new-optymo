package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import com.example.bus_schedules.models.*

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