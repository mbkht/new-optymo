package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.StopTime

@Dao
interface StopTimeDao {
    @Insert
    fun insertAll(vararg stopTimes: StopTime)

    @Query("DELETE FROM stop_times")
    fun deleteAll()
}