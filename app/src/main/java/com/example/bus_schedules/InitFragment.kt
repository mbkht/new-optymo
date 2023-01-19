package com.example.bus_schedules

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bus_schedules.viewmodels.MapViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class InitFragment : Fragment() {

    private lateinit var locationPermissionRequest: ActivityResultLauncher<Array<String>>
    private val mapViewModel: MapViewModel by viewModels()

    private lateinit var btnContinue : Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                    mapViewModel.changeLocationPermission(true)
                    val mapFragment = MapViewFragment()
                    (activity as MainActivity).onFragmentChange(mapFragment)
                }
                permissions.getOrDefault(ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                } else -> {
                // No location access granted.
            }
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentView = inflater.inflate(R.layout.init_fragment, container, false)
        btnContinue = fragmentView.findViewById(R.id.fragment_init_btn_continue)
        btnContinue.setOnClickListener {
            enableLocation()
        }
        return fragmentView
    }

    private fun enableLocation() {
        locationPermissionRequest.launch(arrayOf(
            ACCESS_FINE_LOCATION,
            ACCESS_COARSE_LOCATION
        ))
    }

}
