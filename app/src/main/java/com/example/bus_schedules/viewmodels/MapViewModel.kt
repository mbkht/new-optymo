package com.example.bus_schedules.viewmodels

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_schedules.ui.MapViewUiEvent
import com.example.bus_schedules.ui.MapViewState
import com.example.bus_schedules.repository.Repository
import com.example.bus_schedules.models.Shape
import com.example.bus_schedules.models.Stop
import com.example.bus_schedules.ui.Reducer
import com.example.bus_schedules.ui.TimeCapsule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: Repository,
    private val dispatcher: CoroutineDispatcher,
) : ViewModel() {

    private val _routesList = MutableLiveData(Pair(0, emptyList<Shape>()))
    val routesList: LiveData<Pair<Int, List<Shape>>> = _routesList

    private val _stopList: MutableLiveData<List<Stop>> by lazy {
        MutableLiveData<List<Stop>>()
    }
    val stopList: LiveData<List<Stop>> = _stopList

    private val reducer = MainReducer(MapViewState.initial())

    val state: StateFlow<MapViewState>
        get() = reducer.state

    val timeMachine: TimeCapsule<MapViewState>
        get() = reducer.timeCapsule

    init {
        viewModelScope.launch(dispatcher) {

        }
    }

    private fun sendEvent(event: MapViewUiEvent) {
        reducer.sendEvent(event)
    }

    fun loadAllRoutesAndStops() {
        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(this.toString(), "loadAllRoutesAndStops : exception : " + exception.message)
        }

        viewModelScope.launch(handler) {
            _stopList.value = withContext(Dispatchers.IO) {
                loadRoutesStops()
            }
            withContext(Dispatchers.IO) { loadRoutesShapes() }.collect {
                _routesList.value = it
            }
            Log.d("MapViewModel", "loadAllRoutesAndStops : routes and stops loaded")
        }
    }

    private suspend fun loadRoutesShapes(): Flow<Pair<Int, List<Shape>>> {
        return repository.getAllRoutesShapes()
    }

    private fun loadRoutesStops(): List<Stop>{
        return repository.getAllStops()
    }

    private class MainReducer(initial: MapViewState) : Reducer<MapViewState, MapViewUiEvent>(initial) {
        override fun reduce(oldState: MapViewState, event: MapViewUiEvent) {
            when (event) {
                is MapViewUiEvent.GetRoutesAndStops -> {
                    setState(oldState.copy())
                }

                else -> {}
            }
        }
    }

}