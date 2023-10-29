package com.example.bus_schedules.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_schedules.data.models.Stop
import com.example.bus_schedules.data.repository.Repository
import com.example.bus_schedules.ui.state.MapViewState
import com.example.bus_schedules.ui.events.MapViewUiEvent
import com.example.bus_schedules.ui.utils.Reducer
import com.example.bus_schedules.ui.utils.TimeCapsule
import com.example.bus_schedules.ui.state.BottomSheetState
import com.example.bus_schedules.ui.state.SheetState
import com.example.bus_schedules.ui.state.Sheets
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: Repository,
) : ViewModel() {

    private val reducer = MainReducer(MapViewState.initial())

    val state: StateFlow<MapViewState>
        get() = reducer.state

    val timeMachine: TimeCapsule<MapViewState>
        get() = reducer.timeCapsule

    init {
        loadAllRoutesAndStops()
    }

    private fun loadAllRoutesAndStops() {
        viewModelScope.launch {
            try {
                val stops = withContext(Dispatchers.IO) { repository.getAllStops() }
                reducer.sendEvent(MapViewUiEvent.StopsLoaded(stops))

                val routes = withContext(Dispatchers.IO) { repository.getAllRoutesShapes().toList() }
                reducer.sendEvent(MapViewUiEvent.RoutesLoaded(routes))

            } catch (exception: Exception) {
                Log.e(this.toString(), "loadAllRoutesAndStops : exception : ${exception.message}")
            }
        }
    }

    fun onEvent(mapViewUiEvent: MapViewUiEvent) {
        when (mapViewUiEvent) {
            is MapViewUiEvent.GetSchedules -> {
                getSchedules(mapViewUiEvent.stop)
                reducer.sendEvent(mapViewUiEvent)
            }

            MapViewUiEvent.BottomSheetDismissed -> reducer.sendEvent(mapViewUiEvent)
            is MapViewUiEvent.RoutesLoaded -> TODO()
            is MapViewUiEvent.SchedulesLoaded -> TODO()
            is MapViewUiEvent.StopsLoaded -> TODO()
        }
    }

    private fun getSchedules(stop: Stop) {
        viewModelScope.launch {
            val schedules = withContext(Dispatchers.IO) {repository.getNextTrips(stop).toList()}
            reducer.sendEvent(
                MapViewUiEvent.SchedulesLoaded(
                selectedStop = stop,
                schedules = schedules
            ))
        }
    }

    private class MainReducer(initial: MapViewState) :
        Reducer<MapViewState, MapViewUiEvent>(initial) {
        override fun reduce(oldState: MapViewState, event: MapViewUiEvent) {
            when (event) {

                is MapViewUiEvent.StopsLoaded -> {
                    Log.d("MapViewEvent", "StopsLoaded with ${event.stops.size} stops.")
                    setState(oldState.copy(stopsList = event.stops))
                }

                is MapViewUiEvent.RoutesLoaded -> {
                    Log.d("MapViewEvent", "RoutesLoaded with ${event.routes.size} routes.")
                    setState(oldState.copy(routesList = event.routes))
                }

                is MapViewUiEvent.SchedulesLoaded -> {
                    Log.d("MapViewEvent", "SchedulesLoaded with selected stop: ${event.selectedStop} and ${event.schedules.size} schedules.")
                    setState(
                        oldState.copy(
                            selectedStop = event.selectedStop,
                            schedules = event.schedules,
                            bottomSheetState = BottomSheetState(
                                sheetState = SheetState.EXPANDED,
                                currentSheet = Sheets.Schedules
                            )
                        )
                    )
                }

                is MapViewUiEvent.GetSchedules -> {
                    Log.d("MapViewEvent", "GetSchedules event triggered.")
                    setState(
                        oldState.copy(
                            selectedStop = event.stop,
                            bottomSheetState = BottomSheetState(
                                sheetState = SheetState.PEEK,
                                currentSheet = Sheets.Schedules
                            )
                        )
                    )
                }

                MapViewUiEvent.BottomSheetDismissed -> {
                    Log.d("MapViewEvent", "BottomSheetDismissed event triggered.")
                    setState(
                        oldState.copy(
                            bottomSheetState = BottomSheetState(
                                sheetState = SheetState.PEEK,
                                currentSheet = Sheets.Home
                            )
                        )
                    )
                }
            }
        }
    }

}