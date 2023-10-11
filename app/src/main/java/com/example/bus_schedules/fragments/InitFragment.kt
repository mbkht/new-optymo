package com.example.bus_schedules.fragments

import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.ViewCompositionStrategy
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.bus_schedules.MainActivity
import com.example.bus_schedules.ui.screens.LocationPermissionScreen
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

private const val LOCATION_PERMISSION_REQUEST_CODE = 1000

@AndroidEntryPoint
class InitFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                val activity = LocalContext.current.getActivity()
                val coroutineScope = rememberCoroutineScope()

                LocationPermissionScreen(onContinueClick = {
                    coroutineScope.launch {
                        handleLocationPermissions(activity=activity!!) {
                            // Navigate to the MapViewFragment or perform other actions when the permission is granted
                            val mapFragment = MapViewFragment()
                            (requireActivity() as MainActivity).onFragmentChange(mapFragment)
                        }
                    }
                })
            }
        }
    }

    private fun Context.getActivity(): AppCompatActivity? = when (this) {
        is AppCompatActivity -> this
        is ContextWrapper -> baseContext.getActivity()
        else -> null
    }

    private fun handleLocationPermissions(activity: AppCompatActivity, onPermissionGranted: () -> Unit) {
        val permissions = arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )

        if (ContextCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            onPermissionGranted()
        } else {
            ActivityCompat.requestPermissions(activity, permissions, LOCATION_PERMISSION_REQUEST_CODE)
        }
    }
}
