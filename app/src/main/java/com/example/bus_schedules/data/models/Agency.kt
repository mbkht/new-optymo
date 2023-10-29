package com.example.bus_schedules.data.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "agency")
data class Agency(
    @PrimaryKey @ColumnInfo(name = "agency_id") val agencyId: String,
    @ColumnInfo(name = "agency_name") val agencyName: String,
    @ColumnInfo(name = "agency_url") val agencyUrl: String,
    @ColumnInfo(name = "agency_timezone") val agencyTimezone: String,
    @ColumnInfo(name = "agency_lang") val agencyLang: String? = null,
    @ColumnInfo(name = "agency_phone") val agencyPhone: String? = null,
    @ColumnInfo(name = "agency_fare_url") val agencyFareUrl: String? = null,
    @ColumnInfo(name = "agency_email") val agencyEmail: String? = null
)