package com.example.bus_schedules.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bus_schedules.Repository
import com.example.bus_schedules.models.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    val repository: Repository
) : ViewModel() {
    val _nextTrips = MutableLiveData<List<Schedule>>(listOf())
    val nextTrips: MutableLiveData<List<Schedule>> = _nextTrips

    fun onMarkerClick(stopName: String) {

        val handler = CoroutineExceptionHandler { _, exception ->
            println(exception)
        }

        CoroutineScope(Dispatchers.IO).launch(handler) {
            _nextTrips.postValue(repository.getNextTrips(stopName))
        }
    }
}