package com.example.bus_schedules.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "stops")
data class Stop(
    @PrimaryKey @ColumnInfo(name = "rowid") val rowid: String,
    @ColumnInfo(name = "stop_code") val stopCode: String? = null,
    @ColumnInfo(name = "stop_name") val stopName: String? = null,
    @ColumnInfo(name = "stop_desc") val stopDesc: String? = null,
    @ColumnInfo(name = "stop_lat") val stopLat: Double? = null,
    @ColumnInfo(name = "stop_lon") val stopLon: Double? = null,
    @ColumnInfo(name = "zone_id") val zoneId: String? = null,
    @ColumnInfo(name = "stop_url") val stopUrl: String? = null,
    @ColumnInfo(name = "location_type") val locationType: Int? = null,
    @ColumnInfo(name = "parent_station") val parentStation: Int? = null,
    @ColumnInfo(name = "stop_timezone") val stopTimezone: String? = null,
    @ColumnInfo(name = "wheelchair_boarding") val wheelchairBoarding: Int? = null,
    @ColumnInfo(name = "level_id") val levelId: String? = null,
    @ColumnInfo(name = "platform_code") val platformCode: String? = null
)