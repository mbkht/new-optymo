package com.example.bus_schedules.ui

import com.example.bus_schedules.models.Schedule
import com.example.bus_schedules.models.Shape
import com.example.bus_schedules.models.Stop
import com.example.bus_schedules.ui.composables.BottomSheetState

data class MapViewState(
    val stopsList: List<Stop>,
    val routesList: List<Pair<Int, List<List<Shape>>>>,
    val selectedStop: Stop?,
    val schedules: List<Schedule>,
    val bottomSheetState: BottomSheetState,

) : UiState {
    companion object {
        fun initial() = MapViewState(
            stopsList = emptyList(),
            routesList = emptyList(),
            schedules = emptyList(),
            selectedStop = null,
            bottomSheetState = BottomSheetState.initial()
        )
    }
}
