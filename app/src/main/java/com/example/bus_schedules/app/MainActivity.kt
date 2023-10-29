package com.example.bus_schedules.app

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import com.example.bus_schedules.ui.navigation.Navigation
import com.example.bus_schedules.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

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

    private fun checkAppStart(activity: Activity): String {
        return when (activity.checkAppStart()) {
            AppStart.NORMAL -> "mapViewScreen"
            AppStart.FIRST_TIME_VERSION, AppStart.FIRST_TIME -> "init"
        }
    }

}