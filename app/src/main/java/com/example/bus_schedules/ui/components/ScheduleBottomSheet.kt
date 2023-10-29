package com.example.bus_schedules.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bus_schedules.ui.state.MapViewState
import com.example.bus_schedules.ui.state.BottomSheetState
import com.example.bus_schedules.ui.theme.AppTheme

@Composable
fun ScheduleBottomSheet(mapViewState: MapViewState) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        // Title
        Text(
            text = mapViewState.selectedStop?.stopName.toString(),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onBackground,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        // Composable content
        if(mapViewState.schedules.isNotEmpty()){
            LazyColumn(Modifier.fillMaxSize()) {
                items(mapViewState.schedules){
                    ScheduleRow(schedule = it)
                }
            }
        }
    }
}

@Composable
@Preview
fun BottomSheetModalPreview() {
    AppTheme {
        ScheduleBottomSheet(
            mapViewState = MapViewState(
                stopsList = emptyList(),
                routesList = emptyList(),
                selectedStop = null,
                schedules = emptyList(),
                bottomSheetState = BottomSheetState.initial(),
            )
        )
    }
}
