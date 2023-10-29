package com.example.bus_schedules.data.models

import androidx.room.ColumnInfo

data class NextTrip(
    @ColumnInfo(name = "arrival_time") val arrivalTime: String,
    @ColumnInfo(name = "trip_id") val tripId: String
)