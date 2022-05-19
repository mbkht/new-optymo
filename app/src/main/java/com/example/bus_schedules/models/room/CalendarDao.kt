package com.example.bus_schedules.models.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.models.Calendar

@Dao
interface CalendarDao {
    @Insert
    fun insertAll(vararg calendars: Calendar)

    @Query("DELETE FROM calendar")
    fun deleteAll()
}