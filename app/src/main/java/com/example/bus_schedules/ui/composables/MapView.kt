package com.example.bus_schedules.ui.composables

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import com.example.bus_schedules.R
import com.example.bus_schedules.ui.MapViewUiEvent
import com.example.bus_schedules.ui.MapViewState
import com.example.compose.AppTheme
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapViewScreen(mapViewState: MapViewState, onEvent: (MapViewUiEvent) -> Unit) {
    val belfort = LatLng(47.63360284493573, 6.854039877433978)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(belfort, 16f)
    }
    GoogleMap(
        modifier = Modifier.fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isIndoorEnabled = false,
            isBuildingEnabled = false,
            mapStyleOptions = if (isSystemInDarkTheme()) {
                MapStyleOptions
                    .loadRawResourceStyle(LocalContext.current, R.raw.style_json)
            } else null,
            isMyLocationEnabled = true // To change
        ),
        uiSettings = MapUiSettings(tiltGesturesEnabled = false),
    ) {
        mapViewState.stopsList.forEach { stop ->
            stop.stopLat?.let {
                if(stop.stopName != null){
                    Marker(
                        state = MarkerState(position = LatLng(stop.stopLat, stop.stopLat)),
                        title = stop.stopName,
                        onInfoWindowClick = { onEvent(MapViewUiEvent.GetSchedules(stop.stopName)) }
                    )
                }
            }
        }
    }

    LaunchedEffect(key1 = true) {
        onEvent(MapViewUiEvent.GetRoutesAndStops)
    }
}


@Preview
@Composable
fun TestUIPreview() {
    AppTheme() {
    }
}
