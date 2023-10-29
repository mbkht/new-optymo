package com.example.bus_schedules.ui.events

import com.example.bus_schedules.data.models.Schedule
import com.example.bus_schedules.data.models.Shape
import com.example.bus_schedules.data.models.Stop
import com.example.bus_schedules.ui.utils.UiEvent

sealed class MapViewUiEvent : UiEvent {
    data class GetSchedules(val stop: Stop) : MapViewUiEvent()

    data class SchedulesLoaded(val selectedStop: Stop, val schedules: List<Schedule>) : MapViewUiEvent()
    data class StopsLoaded(val stops: List<Stop>) : MapViewUiEvent()
    data class RoutesLoaded(val routes: List<Pair<Int, List<List<Shape>>>>) : MapViewUiEvent()
    object BottomSheetDismissed : MapViewUiEvent()
}