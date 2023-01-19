package com.example.bus_schedules.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.bus_schedules.Repository
import com.example.bus_schedules.models.Schedule
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BottomSheetViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {
    private val _nextTrips = MutableLiveData<List<Schedule>>(listOf())
    val nextTrips: MutableLiveData<List<Schedule>> = _nextTrips

    fun onMarkerClick(stopName: String) {

        val handler = CoroutineExceptionHandler { _, exception ->
            Log.e(this.toString(), "onMarkerClick : exception : " + exception.message)
        }

        CoroutineScope(Dispatchers.IO).launch(handler) {
            _nextTrips.postValue(repository.getNextTrips(stopName))
        }
    }
}