package com.example.getmoving.feature.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.getmoving.R
import com.example.getmoving.ui.components.EmptyState
import com.example.getmoving.ui.theme.GetMovingTheme

@Composable
fun HistoryScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        Text(
            text = stringResource(R.string.history_title),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.height(24.dp))
        EmptyState(
            title = stringResource(R.string.history_empty),
            hint = stringResource(R.string.history_empty_hint),
            icon = Icons.Outlined.DateRange,
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HistoryScreenPreview() {
    GetMovingTheme {
        HistoryScreen()
    }
}
