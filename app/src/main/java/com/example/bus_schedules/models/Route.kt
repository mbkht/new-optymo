package com.example.bus_schedules.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routes")
data class Route(
    @PrimaryKey @ColumnInfo(name = "route_id") val routeId: String,
    @ColumnInfo(name = "agency_id") val agencyId: String? = null,
    @ColumnInfo(name = "route_short_name") val routeShortName: String? = null,
    @ColumnInfo(name = "route_long_name") val routeLongName: String? = null,
    @ColumnInfo(name = "route_desc") val routeDesc: String? = null,
    @ColumnInfo(name = "route_type") val routeType: Int,
    @ColumnInfo(name = "route_url") val routeUrl: String? = null,
    @ColumnInfo(name = "route_color") val routeColor: String? = null,
    @ColumnInfo(name = "route_text_color") val routeTextColor: String? = null,
    @ColumnInfo(name = "route_sort_order") val routeSortOrder: Int? = null,
    @ColumnInfo(name = "continuous_pickup") val continuousPickup: Int? = null,
    @ColumnInfo(name = "continuous_drop_off") val continuousDropOff: Int? = null
)