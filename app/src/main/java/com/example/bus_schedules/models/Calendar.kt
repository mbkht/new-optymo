package com.example.bus_schedules.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "calendar")
data class Calendar(
    @PrimaryKey
    @ColumnInfo(name = "service_id") val serviceId: String,
    @ColumnInfo(name = "monday") val monday: Int? = null,
    @ColumnInfo(name = "tuesday") val tuesday: Int? = null,
    @ColumnInfo(name = "wednesday") val wednesday: Int? = null,
    @ColumnInfo(name = "thursday") val thursday: Int? = null,
    @ColumnInfo(name = "friday") val friday: Int? = null,
    @ColumnInfo(name = "saturday") val saturday: Int? = null,
    @ColumnInfo(name = "sunday") val sunday: Int? = null,
    @ColumnInfo(name = "start_date") val startDate: String? = null,
    @ColumnInfo(name = "end_date") val endDate: String? = null
)