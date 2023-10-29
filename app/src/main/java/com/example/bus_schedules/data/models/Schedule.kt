package com.example.bus_schedules.data.models


data class Schedule(
    val routeNumber: Number,
    val routeColor: Int,
    val destination: String,
    val arrivalTime: String,
    val minLeft: Int
)
