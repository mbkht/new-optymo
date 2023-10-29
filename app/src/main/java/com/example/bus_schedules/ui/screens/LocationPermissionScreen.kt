package com.example.bus_schedules.ui.screens
import android.content.res.Configuration
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bus_schedules.R
import com.example.bus_schedules.ui.theme.AppTheme
import com.google.accompanist.permissions.ExperimentalPermissionsApi

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen(onContinueClick: () -> Unit) {
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
                onClick = onContinueClick,
                modifier = Modifier
                    .padding(top = 16.dp)) {
                Text(text = stringResource(id = R.string.prompt_continue))
            }
        }
    }

}

@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    name = "DefaultPreviewLight", showSystemUi = true, showBackground = true
)
@Composable
fun LocationPermissionScreenPreview() {
    AppTheme {
        LocationPermissionScreen({})
    }
}
