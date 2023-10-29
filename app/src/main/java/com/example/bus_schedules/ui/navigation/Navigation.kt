package com.example.bus_schedules.ui.navigation

import android.Manifest
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bus_schedules.ui.screens.LocationPermissionScreen
import com.example.bus_schedules.ui.screens.MapViewScreen
import com.example.bus_schedules.viewmodels.MapViewModel

@Composable
fun Navigation(startDestination: String) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = startDestination) {
        navigation("locationPermissionScreen", route = "init") {
            composable("locationPermissionScreen") {
                val launcher = rememberLauncherForActivityResult(
                    contract = ActivityResultContracts.RequestMultiplePermissions(),
                    onResult = { permissions ->
                        if (permissions[Manifest.permission.ACCESS_FINE_LOCATION] == true) {
                            navController.navigate("mapViewScreen")
                        }
                    }
                )
                LocationPermissionScreen(
                    onContinueClick = {
                        launcher.launch(
                            arrayOf(
                                Manifest.permission.ACCESS_FINE_LOCATION,
                                Manifest.permission.ACCESS_COARSE_LOCATION
                            )
                        )
                    }
                )
            }
        }
        composable("mapViewScreen") { backStackEntry ->
            val mapViewModel = hiltViewModel<MapViewModel>()
            val mapViewState = mapViewModel.state.collectAsState().value
            MapViewScreen(
                mapViewState = mapViewState,
                onEvent = mapViewModel::onEvent
            )
        }
    }
}