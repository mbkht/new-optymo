package com.example.bus_schedules.ui.composables

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.layout
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp

@Composable
fun RouteIcon(routeColor : Color, routeNumber : String) {

        Text(
            text = routeNumber,
            style = TextStyle(
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
            ),
            textAlign = TextAlign.Center,
            color = Color.White,
            modifier = Modifier
                .background(routeColor, shape = CircleShape)
                .circleLayout()
        )
}

fun Modifier.circleLayout() =
    layout { measurable, constraints ->
        // Measure the composable
        val placeable = measurable.measure(constraints)

        //get the current max dimension to assign width=height
        val currentHeight = placeable.height
        val currentWidth = placeable.width
        val newDiameter = maxOf(currentHeight, currentWidth)

        //assign the dimension and the center position
        layout(newDiameter, newDiameter) {
            // Where the composable gets placed
            placeable.placeRelative((newDiameter-currentWidth)/2, (newDiameter-currentHeight)/2)
        }
    }

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
)
@Composable
fun RouteIconPreview(){
    RouteIcon(Color.Red,"1")
}