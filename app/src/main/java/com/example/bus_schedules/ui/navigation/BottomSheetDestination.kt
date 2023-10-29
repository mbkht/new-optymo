package com.example.bus_schedules.ui.navigation

sealed class BottomSheetDestination(val route: String) {
    object Home : BottomSheetDestination("home")
    object Schedules : BottomSheetDestination("schedules")
}
