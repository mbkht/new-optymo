package com.example.bus_schedules.ui.screens

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SheetState
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.bus_schedules.ui.navigation.BottomSheetDestination
import com.example.bus_schedules.ui.state.MapViewState
import com.example.bus_schedules.ui.events.MapViewUiEvent
import com.example.bus_schedules.ui.state.BottomSheetState
import com.example.bus_schedules.ui.components.HomeBottomSheet
import com.example.bus_schedules.ui.components.MapView
import com.example.bus_schedules.ui.components.ScheduleBottomSheet
import com.example.compose.AppTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapViewScreen(
    mapViewState: MapViewState,
    onEvent: (MapViewUiEvent) -> Unit
) {

    val sheetState = remember { SheetState(skipHiddenState = false, skipPartiallyExpanded = false) }

    val sheetController = rememberBottomSheetScaffoldState(sheetState)

    BottomSheetScaffold(
        sheetContent = {
            SheetContent(mapViewState = mapViewState)
        },
        scaffoldState = sheetController
    ) { contentPadding ->
        MapView(contentPadding, mapViewState, onEvent)
    }

    LaunchedEffect(mapViewState.selectedStop){
        if(mapViewState.selectedStop!=null){
            sheetController.bottomSheetState.expand()
        }
    }
}

@Composable
fun SheetContent(mapViewState: MapViewState) {
    val navController = rememberNavController()
    Column(modifier = Modifier.fillMaxHeight(fraction = 0.4f).animateContentSize()) {
        NavHost(navController, startDestination = BottomSheetDestination.Schedules.route) {
            composable(BottomSheetDestination.Home.route) {
                HomeBottomSheet()
            }
            composable(BottomSheetDestination.Schedules.route) {
                ScheduleBottomSheet(mapViewState = mapViewState)
            }
        }
    }
}

@Preview
@Composable
fun TestUIPreview() {
    AppTheme() {
        MapViewScreen(mapViewState = MapViewState(
            stopsList = emptyList(),
            routesList = emptyList(),
            selectedStop = null,
            schedules = emptyList(),
            bottomSheetState = BottomSheetState.initial()
        ),
            onEvent = {})
    }
}
