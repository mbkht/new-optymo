package com.example.bus_schedules.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_schedules.Repository
import com.example.bus_schedules.models.Shape
import com.example.bus_schedules.models.Stop
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

    private val _routesList = MutableLiveData(Pair(0, emptyList<Shape>()))
    val routesList: LiveData<Pair<Int, List<Shape>>> = _routesList

    private val _stopList: MutableLiveData<List<Stop>> by lazy {
        MutableLiveData<List<Stop>>()
    }
    val stopList: LiveData<List<Stop>> = _stopList

    fun loadRoutesAndStops() {
        val handler = CoroutineExceptionHandler { _, exception ->
            println(exception)
        }

        viewModelScope.launch(handler) {
            _stopList.value = loadRoutesStops()
            loadRoutesShapes().collect {
                _routesList.value = it
            }
        }
    }

    private suspend fun loadRoutesShapes(): Flow<Pair<Int, List<Shape>>> {
        return repository.getAllRoutesShapes()
    }

    private suspend fun loadRoutesStops(): List<Stop>{
        return repository.getAllStops()
    }

}