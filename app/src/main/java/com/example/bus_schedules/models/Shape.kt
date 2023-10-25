package com.example.bus_schedules.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "shapes", primaryKeys = ["shape_id", "shape_pt_sequence"])
data class Shape(
    @ColumnInfo(name = "shape_id") val shapeId: String,
    @ColumnInfo(name = "shape_pt_lat") val shapePtLat: Double,
    @ColumnInfo(name = "shape_pt_lon") val shapePtLon: Double,
    @ColumnInfo(name = "shape_pt_sequence") val shapePtSequence: Int,
    @ColumnInfo(name = "shape_dist_traveled") val shapeDistTraveled: String? = null
)