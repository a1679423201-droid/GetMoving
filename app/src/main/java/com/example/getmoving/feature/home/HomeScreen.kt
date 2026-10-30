package com.example.getmoving.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.getmoving.R
import com.example.getmoving.domain.model.WeightEntry
import com.example.getmoving.domain.rule.WeightInputError
import com.example.getmoving.ui.components.EmptyState
import com.example.getmoving.ui.components.WeightHeroCard
import com.example.getmoving.ui.format.DateFormatter
import com.example.getmoving.ui.format.WeightFormatter
import com.example.getmoving.ui.theme.GetMovingTheme
import java.time.LocalDate

@Composable
fun HomeRoute(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    HomeScreen(
        uiState = uiState,
        onOpenEntry = viewModel::openEntrySheet,
        onDismissEntry = viewModel::dismissEntrySheet,
        onSaveWeight = viewModel::saveWeight,
        onEntryInputChange = viewModel::clearEntryError,
        modifier = modifier,
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    onOpenEntry: () -> Unit,
    onDismissEntry: () -> Unit,
    onSaveWeight: (String) -> Unit,
    onEntryInputChange: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 24.dp),
    ) {
        Text(
            text = DateFormatter.full(uiState.today),
            style = MaterialTheme.typography.headlineSmall,
        )
        Spacer(Modifier.height(20.dp))

        WeightHeroCard(
            title = stringResource(R.string.home_today_label),
            weightText = uiState.todayEntry?.let { WeightFormatter.formatKg(it.weightTenths) },
            goalText = goalText(uiState),
            emptyHint = stringResource(R.string.home_today_no_entry),
            actionLabel = stringResource(R.string.home_record_weight),
            onAction = onOpenEntry,
        )

        Spacer(Modifier.height(32.dp))
        Text(
            text = stringResource(R.string.home_recent_seven_days),
            style = MaterialTheme.typography.titleMedium,
        )
        Spacer(Modifier.height(12.dp))

        RecentWeightCard(
            entries = uiState.recentEntries,
            emptyContent = {
                EmptyState(
                    title = stringResource(R.string.home_no_recent),
                    hint = stringResource(R.string.home_no_recent_hint),
                    icon = Icons.Outlined.DateRange,
                )
            },
        )
    }

    if (uiState.isEntrySheetVisible) {
        WeightEntrySheet(
            initialInput = uiState.todayEntry
                ?.let { WeightFormatter.formatPlainTenths(it.weightTenths) }
                .orEmpty(),
            isSaving = uiState.isSaving,
            error = uiState.error,
            onDismiss = onDismissEntry,
            onSave = onSaveWeight,
            onInputChange = onEntryInputChange,
        )
    }
}

@Composable
private fun RecentWeightCard(
    entries: List<WeightEntry>,
    emptyContent: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.large,
        color = MaterialTheme.colorScheme.surfaceContainer,
    ) {
        if (entries.isEmpty()) {
            emptyContent()
        } else {
            Column(modifier = Modifier.padding(horizontal = 16.dp)) {
                entries.forEachIndexed { index, entry ->
                    RecentWeightRow(entry)
                    if (index != entries.lastIndex) {
                        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    }
                }
            }
        }
    }
}

@Composable
private fun goalText(uiState: HomeUiState): String? {
    if (uiState.targetWeightTenths == null) {
        return if (uiState.todayEntry != null) {
            stringResource(R.string.home_goal_unset)
        } else {
            null
        }
    }
    val delta = uiState.goalDeltaTenths ?: return null
    return when {
        delta > 0 -> stringResource(
            R.string.home_goal_above,
            WeightFormatter.formatDeltaKg(delta),
        )

        delta < 0 -> stringResource(
            R.string.home_goal_below,
            WeightFormatter.formatDeltaKg(delta),
        )

        else -> stringResource(R.string.home_goal_reached)
    }
}

@Composable
private fun RecentWeightRow(entry: WeightEntry) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = DateFormatter.short(entry.date),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.width(72.dp),
        )
        Text(
            text = WeightFormatter.formatKg(entry.weightTenths),
            style = MaterialTheme.typography.bodyLarge,
            modifier = Modifier.weight(1f),
        )
        if (entry.isBackfill) {
            Surface(
                shape = MaterialTheme.shapes.small,
                color = MaterialTheme.colorScheme.secondaryContainer,
            ) {
                Text(
                    text = stringResource(R.string.backfill_tag),
                    style = MaterialTheme.typography.labelSmall,
                    modifier = Modifier.padding(horizontal = 8.dp, vertical = 2.dp),
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun WeightEntrySheet(
    initialInput: String,
    isSaving: Boolean,
    error: WeightInputError?,
    onDismiss: () -> Unit,
    onSave: (String) -> Unit,
    onInputChange: () -> Unit,
) {
    var input by rememberSaveable { mutableStateOf(initialInput) }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .imePadding()
                .padding(start = 20.dp, end = 20.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            Text(
                text = stringResource(R.string.entry_title),
                style = MaterialTheme.typography.headlineSmall,
            )
            OutlinedTextField(
                value = input,
                onValueChange = {
                    input = it
                    onInputChange()
                },
                modifier = Modifier.fillMaxWidth(),
                label = { Text(stringResource(R.string.entry_label)) },
                suffix = { Text(stringResource(R.string.entry_unit)) },
                singleLine = true,
                isError = error != null,
                supportingText = {
                    if (error != null) {
                        Text(error.label())
                    }
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Decimal,
                    imeAction = ImeAction.Done,
                ),
            )
            Button(
                onClick = { onSave(input) },
                enabled = !isSaving,
                modifier = Modifier.fillMaxWidth(),
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                } else {
                    Text(stringResource(R.string.action_save))
                }
            }
        }
    }
}

@Composable
private fun WeightInputError.label(): String = when (this) {
    WeightInputError.Empty -> stringResource(R.string.weight_error_empty)
    WeightInputError.NotANumber -> stringResource(R.string.weight_error_not_a_number)
    WeightInputError.TooManyDecimals -> stringResource(R.string.weight_error_too_many_decimals)
    WeightInputError.OutOfRange -> stringResource(R.string.weight_error_out_of_range)
    WeightInputError.SaveFailed -> stringResource(R.string.weight_error_save_failed)
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenEmptyPreview() {
    GetMovingTheme {
        HomeScreen(
            uiState = HomeUiState(
                today = LocalDate.of(2026, 10, 29),
                isLoading = false,
            ),
            onOpenEntry = {},
            onDismissEntry = {},
            onSaveWeight = {},
            onEntryInputChange = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenRecordedPreview() {
    val today = LocalDate.of(2026, 10, 29)
    GetMovingTheme {
        HomeScreen(
            uiState = HomeUiState(
                today = today,
                todayEntry = WeightEntry(today, 705, isBackfill = false),
                recentEntries = listOf(
                    WeightEntry(today, 705, isBackfill = false),
                    WeightEntry(today.minusDays(1), 702, isBackfill = true),
                    WeightEntry(today.minusDays(2), 708, isBackfill = false),
                ),
                targetWeightTenths = 650,
                isLoading = false,
            ),
            onOpenEntry = {},
            onDismissEntry = {},
            onSaveWeight = {},
            onEntryInputChange = {},
        )
    }
}
