package com.example.bus_schedules.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "trips")
data class Trip(
    @ColumnInfo(name = "route_id") val routeId: Int,
    @ColumnInfo(name = "service_id") val serviceId: String,
    @PrimaryKey @ColumnInfo(name = "trip_id") val tripId: String,
    @ColumnInfo(name = "trip_headsign") val tripHeadsign: String? = null,
    @ColumnInfo(name = "trip_short_name") val tripShortName: String? = null,
    @ColumnInfo(name = "direction_id") val directionId: Int? = null,
    @ColumnInfo(name = "block_id") val blockId: String? = null,
    @ColumnInfo(name = "shape_id") val shapeId: String? = null,
    @ColumnInfo(name = "wheelchair_accessible") val wheelchairAccessible: Int? = null,
    @ColumnInfo(name = "bikes_allowed") val bikesAllowed: Int? = null
)