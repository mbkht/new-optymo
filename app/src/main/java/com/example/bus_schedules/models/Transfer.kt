package com.example.bus_schedules.models

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "transfers", primaryKeys = ["from_stop_id", "to_stop_id"])
data class Transfer(
    @ColumnInfo(name = "from_stop_id") val fromStopId: Int,
    @ColumnInfo(name = "to_stop_id") val toStopId: Int,
    @ColumnInfo(name = "transfer_type") val transferType: Int,
    @ColumnInfo(name = "min_transfer_time") val minTransferTime: Int? = null
)