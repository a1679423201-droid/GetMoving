package com.example.getmoving.feature.home

import com.example.getmoving.domain.model.WeightEntry
import com.example.getmoving.domain.rule.GoalProgress
import com.example.getmoving.domain.rule.WeightInputError
import java.time.LocalDate

data class HomeUiState(
    val today: LocalDate,
    val todayEntry: WeightEntry? = null,
    val recentEntries: List<WeightEntry> = emptyList(),
    val targetWeightTenths: Int? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    val isEntrySheetVisible: Boolean = false,
    val error: WeightInputError? = null,
) {
    val goalDeltaTenths: Int?
        get() {
            val current = todayEntry?.weightTenths ?: return null
            val target = targetWeightTenths ?: return null
            return GoalProgress.deltaTenths(current, target)
        }
}
