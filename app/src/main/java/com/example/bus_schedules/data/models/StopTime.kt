package com.example.bus_schedules.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "stop_times", primaryKeys = ["trip_id", "rowid", "stop_sequence"])
data class StopTime(
    @ColumnInfo(name = "trip_id") val tripId: String,
    @ColumnInfo(name = "arrival_time") val arrivalTime: String? = null,
    @ColumnInfo(name = "departure_time") val departureTime: String? = null,
    @ColumnInfo(name = "rowid") val rowid: Int,
    @ColumnInfo(name = "stop_sequence") val stopSequence: Int,
    @ColumnInfo(name = "stop_headsign") val stopHeadsign: String? = null,
    @ColumnInfo(name = "pickup_type") val pickupType: Int? = null,
    @ColumnInfo(name = "drop_off_type") val dropOffType: Int? = null,
    @ColumnInfo(name = "continuous_pickup") val continuousPickup: Int? = null,
    @ColumnInfo(name = "continuous_drop_off") val continuousDropOff: Int? = null,
    @ColumnInfo(name = "shape_dist_traveled") val shapeDistTraveled: String? = null,
    @ColumnInfo(name = "timepoint") val timepoint: Int? = null
)