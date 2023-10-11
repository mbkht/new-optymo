package com.example.bus_schedules.ui

import com.example.bus_schedules.models.Route
import com.example.bus_schedules.models.Stop

data class MapViewState (
    val stopsList: List<Stop>,
    val routesList: List<Route>
) : UiState {
    companion object {
        fun initial() = MapViewState(
            stopsList = emptyList(),
            routesList = emptyList()
        )


    }
}
