package com.example.bus_schedules.ui.state

import com.example.bus_schedules.data.models.Schedule
import com.example.bus_schedules.data.models.Shape
import com.example.bus_schedules.data.models.Stop
import com.example.bus_schedules.ui.utils.UiState

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
