package com.example.getmoving.domain.model

import java.time.LocalDate

data class WeightEntry(
    val date: LocalDate,
    val weightTenths: Int,
    val isBackfill: Boolean,
)
