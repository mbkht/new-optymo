package com.example.bus_schedules

import android.Manifest
import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.example.bus_schedules.ui.screens.locationpermission.LocationPermissionScreen
import com.example.bus_schedules.ui.screens.mapview.MapViewScreen
import com.example.bus_schedules.ui.screens.mapview.MapViewModel
import com.example.compose.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val mapViewModel: MapViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startDestination = checkAppStart(this)
        setContent {
            AppTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    Navigation(startDestination)
                }
            }
        }
    }


    @Composable
    private fun Navigation(startDestination: String) {
        val navController = rememberNavController()
        NavHost(navController = navController, startDestination = startDestination) {
            navigation("locationPermissionScreen", route = "init") {
                composable("locationPermissionScreen") {
                    LocationPermissionScreenNav(navController = navController)
                }
            }
            composable("mapViewScreen") {
                MapViewScreenNav()
            }
        }
    }

    @Composable
    private fun LocationPermissionScreenNav(navController: NavController) {
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

    @Composable
    private fun MapViewScreenNav() {
        val mapViewState = mapViewModel.state.collectAsState().value
        MapViewScreen(
            mapViewState = mapViewState,
            onEvent = mapViewModel::onEvent
        )
    }

    private fun checkAppStart(activity: Activity): String {
        return when (activity.checkAppStart()) {
            AppStart.NORMAL -> "mapViewScreen"
            AppStart.FIRST_TIME_VERSION, AppStart.FIRST_TIME -> "init"
        }
    }

}