package com.example.bus_schedules.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.bus_schedules.R
import com.example.compose.AppTheme

@Composable
fun HomeBottomSheet() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primary)
    ) {

        // Title
        Text(
            text = stringResource(id = R.string.bottom_sheet_title),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.onPrimary,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .height(52.dp)
                .fillMaxWidth()
                .padding(vertical = 8.dp) // Optional for spacing
        )

        // Composable content
        Box(
            modifier = Modifier
                .fillMaxWidth()
        ) {

        }
    }
}

@Composable
@Preview
fun HomeBottomSheetPreview() {
    AppTheme {
        HomeBottomSheet()
    }
}
