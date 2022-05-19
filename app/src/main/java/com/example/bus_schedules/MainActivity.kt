package com.example.bus_schedules

import android.content.Context
import android.content.res.Configuration
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_schedules.viewmodels.BottomSheetViewModel
import com.example.bus_schedules.viewmodels.MainViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()
    private val bottomSheetViewModel: BottomSheetViewModel by viewModels()

    private lateinit var mapView: MapView
    private lateinit var bottomSheet: BottomSheetBehavior<View>
    private lateinit var recyclerView: RecyclerView
    private lateinit var scheduleAdapter: ScheduleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mapViewBundle = savedInstanceState?.getBundle(MAPVIEW_BUNDLE_KEY)

        mapView = findViewById(R.id.map)
        mapView.onCreate(mapViewBundle)
        mapView.getMapAsync(this)

        bottomSheet = BottomSheetBehavior.from(findViewById(R.id.standard_bottom_sheet))
        recyclerView = findViewById(R.id.bottom_sheet_content)
        recyclerView.layoutManager = LinearLayoutManager(this)
        scheduleAdapter = ScheduleAdapter(listOf())
        recyclerView.adapter = scheduleAdapter


        viewModel.loadRoutesAndStops()
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

    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        return ContextCompat.getDrawable(context, vectorResId)?.run {
            setBounds(0, 0, intrinsicWidth, intrinsicHeight)
            val bitmap =
                Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }

    override fun onMapReady(map: GoogleMap) {
        // Bus stop icon
        val bitmapdescriptor =
            bitmapDescriptorFromVector(applicationContext, R.drawable.ic_bus_stop)
        // If night mod, apply the night map style
        if(resources.configuration.uiMode and
            Configuration.UI_MODE_NIGHT_MASK == UI_MODE_NIGHT_YES){
            map.setMapStyle(MapStyleOptions.loadRawResourceStyle(applicationContext, R.raw.style_json))
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
                    LatLng(shape.shapePtLat!!, shape.shapePtLon!!)
                })
                map.addPolyline(polylineOptions)
            }
        }
        viewModel.stopList.observe(this) {
            it.forEach { stop ->
                map.addMarker(
                    MarkerOptions()
                        .title(stop.stopName)
                        .icon(bitmapdescriptor)
                        .position(LatLng(stop.stopLat!!, stop.stopLon!!))
                )
            }
        }
        map.setOnMarkerClickListener {
            val text = this.findViewById(R.id.bottom_sheet_title) as TextView
            text.text = it.title!!
            bottomSheetViewModel.onMarkerClick(it.title!!)
            true
        }
        bottomSheetViewModel.nextTrips.observe(this) {
            if (it.isNullOrEmpty()) {
                recyclerView.visibility = View.INVISIBLE
            } else {
                recyclerView.visibility = View.VISIBLE
                scheduleAdapter.schedulesList = it
                recyclerView.adapter = scheduleAdapter
            }
        }
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
}