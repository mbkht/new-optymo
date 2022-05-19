package com.example.bus_schedules

import android.graphics.Color
import android.graphics.PorterDuff
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.bus_schedules.models.Schedule
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

class ScheduleAdapter(var schedulesList: List<Schedule>) :
    RecyclerView.Adapter<ScheduleAdapter.ViewHolder>() {

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder).
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tripHeadsignText: TextView
        val routeIcon: TextView
        val timeLeftText: TextView
        val arrivalTimeText: TextView

        init {
            // Define click listener for the ViewHolder's View.
            routeIcon = view.findViewById(R.id.route_icon)
            tripHeadsignText = view.findViewById(R.id.trip_headsign_text)
            timeLeftText = view.findViewById(R.id.time_left_text)
            arrivalTimeText = view.findViewById(R.id.arrival_time_text)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.schedule_row, viewGroup, false)

        return ViewHolder(view)
    }

    private fun getTimeLeft(arrivalTimeString: String): Int {
        val arrivalTime = LocalTime.parse(arrivalTimeString, DateTimeFormatter.ofPattern("HH:mm"))
        val currentTime = LocalTime.now()
        val diff = Math.abs(ChronoUnit.MINUTES.between(arrivalTime, currentTime)).toInt()
        if (Math.abs(diff) > 30.0) {
            return -1
        } else {
            return diff
        }
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        val schedule = schedulesList[position]


        // Route Icon
        val busIconDrawable =
            ContextCompat.getDrawable(viewHolder.itemView.context, R.drawable.bus_icon_bg)
        busIconDrawable!!.colorFilter = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
            Color.parseColor("#" + schedule.routeColor),
            BlendModeCompat.SRC_ATOP
        )
        viewHolder.routeIcon.setBackground(busIconDrawable)
        viewHolder.routeIcon.background
        viewHolder.routeIcon.text = schedule.trip.routeId.toString()

        // Trip Headsign
        viewHolder.tripHeadsignText.text = schedule.trip.tripHeadsign

        // Time left and arrival time
        val timeLeft = getTimeLeft(schedule.arrivalTime)
        if (timeLeft != -1) {
            viewHolder.timeLeftText.text =
                if (timeLeft == 0) "en approche" else
                    String.format("%d min", timeLeft)
            viewHolder.arrivalTimeText.text = String.format("Prévu à %s", schedule.arrivalTime)
        } else {
            viewHolder.timeLeftText.textSize = 18.0F
            viewHolder.timeLeftText.text = String.format("Prévu à %s", schedule.arrivalTime)
            viewHolder.arrivalTimeText.text = ""
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = schedulesList.size
}