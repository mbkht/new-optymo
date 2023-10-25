package com.example.bus_schedules.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.bus_schedules.R
import com.example.bus_schedules.models.Schedule
import com.example.compose.AppTheme

@Composable
fun ScheduleRow(schedule: Schedule) {
    val routeNumber = schedule.routeNumber.toString()
    val routeColor = Color(schedule.routeColor)
    val destination = schedule.destination
    val arrivalTime = schedule.arrivalTime
    val minLeft = schedule.minLeft
    val minLeftString = when(minLeft){
        0 -> stringResource(id = R.string.min_left_approaching, minLeft)
        -1 -> arrivalTime
        else -> {
            stringResource(id = R.string.min_left_text, minLeft)
        }
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 10.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.padding(start = 5.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RouteIcon(routeNumber = routeNumber, routeColor = routeColor)

                Text(
                    text = destination,
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }

            Text(
                text = minLeftString,
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.End,
                modifier = Modifier
                    .padding(4.dp)
            )
        }

        if(minLeft != -1){
            Text(
                text = stringResource(id = R.string.arrival_time_text, arrivalTime),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }

        Divider(
            color = MaterialTheme.colorScheme.onSecondaryContainer,
            thickness = 1.dp,
            modifier = Modifier.padding(top = 10.dp)
        )
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO, showBackground = true, showSystemUi = true,
)
@Composable
fun ScheduleRowPreview() {
    AppTheme {
        val schedule = Schedule(
            1,
            android.graphics.Color.parseColor("#FF0000"),
            "Justice",
            "7:45",
            -1
        )
        ScheduleRow(schedule)
    }
}