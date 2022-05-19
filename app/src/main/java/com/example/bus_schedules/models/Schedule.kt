package com.example.bus_schedules.models

data class Schedule(
    val arrivalTime: String,
    val trip: Trip,
    val routeColor: String
)
