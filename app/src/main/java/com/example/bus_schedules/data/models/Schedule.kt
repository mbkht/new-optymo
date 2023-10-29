package com.example.bus_schedules.data.models

import android.graphics.Color


data class Schedule(
    val routeNumber: Number,
    val routeColor: Int,
    val destination: String,
    val arrivalTime: String,
    val minLeft: Int
)
