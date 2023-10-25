package com.example.bus_schedules.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "calendar_dates", primaryKeys = ["service_id", "date"])
data class CalendarDate(
    @ColumnInfo(name = "service_id") val serviceId: String,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "exception_type") val exceptionType: Int
)