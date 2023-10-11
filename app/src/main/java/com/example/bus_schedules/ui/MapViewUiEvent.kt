package com.example.bus_schedules.ui

import com.example.bus_schedules.models.Stop

sealed class MapViewUiEvent : UiEvent {
    object GetRoutesAndStops : MapViewUiEvent()
    data class GetSchedules(val stopName: String) : MapViewUiEvent()
}