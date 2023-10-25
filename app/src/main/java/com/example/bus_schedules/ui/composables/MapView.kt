package com.example.bus_schedules.ui.composables

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.bus_schedules.R
import com.example.bus_schedules.ui.MapViewState
import com.example.bus_schedules.ui.MapViewUiEvent
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MapStyleOptions
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.Polyline
import com.google.maps.android.compose.rememberCameraPositionState

private const val BELFORT_LATITUDE = 47.63360284493573
private const val BELFORT_LONGITUDE = 6.854039877433978
private const val INITIAL_ZOOM_LEVEL = 16f

@Composable
fun MapView(
    contentPadding: PaddingValues,
    mapViewState: MapViewState,
    onEvent: (MapViewUiEvent) -> Unit
) {
    val belfort = LatLng(BELFORT_LATITUDE, BELFORT_LONGITUDE)
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(belfort, INITIAL_ZOOM_LEVEL)
    }
    GoogleMap(
        modifier = Modifier
            .padding(contentPadding)
            .fillMaxSize(),
        cameraPositionState = cameraPositionState,
        properties = MapProperties(
            isIndoorEnabled = false,
            isBuildingEnabled = false,
            mapStyleOptions = if (isSystemInDarkTheme()) {
                MapStyleOptions.loadRawResourceStyle(LocalContext.current, R.raw.style_json)
            } else null,
            isMyLocationEnabled = true // To change
        ),
        uiSettings = MapUiSettings(tiltGesturesEnabled = false),
    ) {

        mapViewState.stopsList.forEach { stop ->
            if (stop.stopName != null && stop.stopLat != null && stop.stopLon != null) {
                Marker(
                    icon=BitmapDescriptorFactory.fromResource(R.drawable.ic_bus_stop),
                    state = MarkerState(position = LatLng(stop.stopLat, stop.stopLon)),
                    onClick = {
                        onEvent(MapViewUiEvent.GetSchedules(stop))
                        true
                    },
                )
            }
        }

        mapViewState.routesList.forEach { route ->
            val color = Color(route.first)
            route.second.forEach { trip ->
                val points = trip.map { LatLng(it.shapePtLat, it.shapePtLon) }
                Polyline(points = points, color = color, geodesic = true)
            }
        }
    }
}