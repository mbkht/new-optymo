package com.example.bus_schedules
import android.Manifest
import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.compose.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberMultiplePermissionsState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen() {
    val locationPermissionState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    if(locationPermissionState.permissions[0].status.isGranted){
        val mapFragment = MapViewFragment()
        (LocalContext.current.getActivity() as MainActivity).onFragmentChange(mapFragment)
    }
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_location_permission),
                contentDescription = stringResource(id = R.string.location_permission_description),
            )
            Text(
                text = stringResource(id = R.string.location_permission_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.location_permission_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { locationPermissionState.launchMultiplePermissionRequest()},
                modifier = Modifier
                    .padding(top = 16.dp)) {
                Text(text = stringResource(id = R.string.prompt_continue))
            }
        }
    }

}

fun Context.getActivity(): AppCompatActivity? = when (this) {
    is AppCompatActivity -> this
    is ContextWrapper -> baseContext.getActivity()
    else -> null
}

@Composable
fun MockedLocationPermissionScreen() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_location_permission),
                contentDescription = stringResource(id = R.string.location_permission_description),
            )
            Text(
                text = stringResource(id = R.string.location_permission_title),
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(top = 16.dp)
            )
            Text(
                text = stringResource(id = R.string.location_permission_description),
                style = MaterialTheme.typography.bodyLarge,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 16.dp)
            )

            Button(
                onClick = { },
                modifier = Modifier
                    .padding(top = 16.dp)) {
                Text(text = stringResource(id = R.string.prompt_continue))
            }
        }
    }
}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    name = "DefaultPreviewDark"
)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight"
)
@Composable
fun LocationPermissionScreenPreview() {
    AppTheme {
        MockedLocationPermissionScreen()
    }
}