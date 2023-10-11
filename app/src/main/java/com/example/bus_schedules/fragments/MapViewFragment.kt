package com.example.bus_schedules.fragments

import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.bus_schedules.R
import com.example.bus_schedules.ui.composables.ScheduleRow
import com.example.bus_schedules.viewmodels.BottomSheetViewModel
import com.example.bus_schedules.viewmodels.MapViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapViewFragment : Fragment(), OnMapReadyCallback {

    private val viewModel: MapViewModel by viewModels()
    private val bottomSheetViewModel: BottomSheetViewModel by viewModels()

    private lateinit var fragmentView : View
    private lateinit var mapView: MapView
    private lateinit var bottomSheet: BottomSheetBehavior<View>
    private lateinit var bottomSheetContent: ComposeView

    private lateinit var map: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        fragmentView = inflater.inflate(R.layout.mapview_fragment, container, false)
        mapView = fragmentView.findViewById(R.id.map)!!
        mapView.onCreate(null)
        mapView.getMapAsync(this)

        bottomSheet = BottomSheetBehavior.from(fragmentView.findViewById(R.id.standard_bottom_sheet)!!)
        bottomSheet.isHideable = false
        bottomSheet.maxHeight = 1000

        // bottomSheet Content
        bottomSheetContent = fragmentView.findViewById(R.id.bottom_sheet_content)
        bottomSheetContent.setContent {
            val schedules by bottomSheetViewModel.nextTrips.observeAsState(initial = listOf())
            LazyColumn(Modifier.fillMaxSize()) {
               items(schedules){
                   ScheduleRow(schedule = it)
               }
            }
        }

        viewModel.loadAllRoutesAndStops()

        return fragmentView
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        MapsInitializer.initialize(context, MapsInitializer.Renderer.LATEST, null)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onMapReady(map: GoogleMap) {
        val fragmentContext = requireContext()
        this.map = map
        // Bus stop icon
        val bitmapDescriptor =
            bitmapDescriptorFromVector(fragmentContext, R.drawable.ic_bus_stop)
        // If night mod, apply the night map style
        if(resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == Configuration.UI_MODE_NIGHT_YES
        ){
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(fragmentContext, R.raw.style_json))
        }
        map.isIndoorEnabled = false
        map.uiSettings.isTiltGesturesEnabled = false
        map.isBuildingsEnabled = false
        viewModel.routesList.observe(this) { route ->
            val routeColor = route.first
            val shapesList = route.second.groupBy { shape ->
                shape.shapeId
            }.values
            shapesList.forEach { shapeSubList ->
                val polylineOptions = PolylineOptions()
                polylineOptions.color(routeColor)
                polylineOptions.addAll(shapeSubList.map { shape ->
                    LatLng(shape.shapePtLat, shape.shapePtLon)
                })
                map.addPolyline(polylineOptions)
            }
        }
        viewModel.stopList.observe(this) {
            it.forEach { stop ->
                map.addMarker(
                    MarkerOptions()
                        .title(stop.stopName)
                        .icon(bitmapDescriptor)
                        .position(LatLng(stop.stopLat!!, stop.stopLon!!))
                )
            }
        }
        map.setOnMarkerClickListener {
            val text : TextView = fragmentView.findViewById(R.id.bottom_sheet_title)!!
            text.text = it.title!!
            bottomSheetViewModel.onMarkerClick(it.title!!)
            true
        }
        enableMyLocation()
    }

    private fun enableMyLocation() {

        // Check if permissions are granted, if so, enable the my location layer
        if (ContextCompat.checkSelfPermission(
                requireActivity(),
                ACCESS_FINE_LOCATION
            ) == PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
                requireActivity(),
                ACCESS_COARSE_LOCATION
            ) == PERMISSION_GRANTED
        ) {
            map.isMyLocationEnabled = true
            return
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        val mapViewBundle = outState.getBundle(MAPVIEW_BUNDLE_KEY) ?: Bundle().also {
            outState.putBundle(MAPVIEW_BUNDLE_KEY, it)
        }
        mapView.onSaveInstanceState(mapViewBundle)
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    companion object {
        private const val MAPVIEW_BUNDLE_KEY = "MapViewBundleKey"
    }

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

}
