package com.example.bus_schedules.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "feed_info")
data class FeedInfo(
    @PrimaryKey
    @ColumnInfo(name = "feed_publisher_name") val feedPublisherName: String,
    @ColumnInfo(name = "feed_publisher_url") val feedPublisherUrl: String? = null,
    @ColumnInfo(name = "feed_lang") val feedLang: String? = null,
    @ColumnInfo(name = "default_lang") val defaultLang: String? = null,
    @ColumnInfo(name = "feed_start_date") val feedStartDate: String? = null,
    @ColumnInfo(name = "feed_end_date") val feedEndDate: String? = null,
    @ColumnInfo(name = "feed_version") val feedVersion: String? = null,
    @ColumnInfo(name = "feed_contact_email") val feedContactEmail: String? = null,
    @ColumnInfo(name = "feed_contact_url") val feedContactUrl: String? = null
)