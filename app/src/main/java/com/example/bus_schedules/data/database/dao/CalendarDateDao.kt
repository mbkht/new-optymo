package com.example.bus_schedules.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.bus_schedules.data.models.CalendarDate

@Dao
interface CalendarDateDao {
    @Insert
    fun insertAll(vararg calendarDates: CalendarDate)

    @Query("DELETE FROM calendar_dates")
    fun deleteAll()
}