package com.example.bus_schedules.ui

import com.example.bus_schedules.models.Schedule
import com.example.bus_schedules.models.Shape
import com.example.bus_schedules.models.Stop

sealed class MapViewUiEvent : UiEvent {
    data class GetSchedules(val stop: Stop) : MapViewUiEvent()

    data class SchedulesLoaded(val selectedStop: Stop, val schedules: List<Schedule>) : MapViewUiEvent()
    data class StopsLoaded(val stops: List<Stop>) : MapViewUiEvent()
    data class RoutesLoaded(val routes: List<Pair<Int, List<List<Shape>>>>) : MapViewUiEvent()
    object BottomSheetDismissed : MapViewUiEvent()
}