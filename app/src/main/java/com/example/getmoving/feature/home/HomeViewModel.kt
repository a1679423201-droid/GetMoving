package com.example.getmoving.feature.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.getmoving.domain.repository.SettingsRepository
import com.example.getmoving.domain.repository.WeightRepository
import com.example.getmoving.domain.rule.WeightInputError
import com.example.getmoving.domain.rule.WeightParseResult
import com.example.getmoving.domain.rule.WeightValidator
import dagger.hilt.android.lifecycle.HiltViewModel
import java.time.Clock
import java.time.LocalDate
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val weightRepository: WeightRepository,
    private val settingsRepository: SettingsRepository,
    clock: Clock,
) : ViewModel() {

    private val today: LocalDate = LocalDate.now(clock)
    private val _uiState = MutableStateFlow(HomeUiState(today = today))
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            combine(
                weightRepository.observeRecent(RECENT_DAYS),
                settingsRepository.observeTargetWeightTenths(),
            ) { recent, target -> recent to target }
                .collect { (recent, target) ->
                    _uiState.update { state ->
                        state.copy(
                            recentEntries = recent,
                            todayEntry = recent.firstOrNull { it.date == today },
                            targetWeightTenths = target,
                            isLoading = false,
                        )
                    }
                }
        }
    }

    fun openEntrySheet() {
        _uiState.update { it.copy(isEntrySheetVisible = true, error = null) }
    }

    fun dismissEntrySheet() {
        _uiState.update { it.copy(isEntrySheetVisible = false, error = null) }
    }

    fun clearEntryError() {
        _uiState.update { if (it.error == null) it else it.copy(error = null) }
    }

    fun saveWeight(input: String) {
        when (val result = WeightValidator.parse(input)) {
            is WeightParseResult.Invalid ->
                _uiState.update { it.copy(error = result.error) }

            is WeightParseResult.Valid -> viewModelScope.launch {
                _uiState.update { it.copy(isSaving = true, error = null) }
                val failure = runCatching {
                    weightRepository.upsert(today, result.tenths, isBackfill = false)
                }.exceptionOrNull()

                _uiState.update { state ->
                    if (failure == null) {
                        state.copy(isSaving = false, isEntrySheetVisible = false)
                    } else {
                        state.copy(isSaving = false, error = WeightInputError.SaveFailed)
                    }
                }
            }
        }
    }

    private companion object {
        const val RECENT_DAYS = 7
    }
}
