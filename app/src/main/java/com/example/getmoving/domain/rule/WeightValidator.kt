package com.example.getmoving.domain.rule

import java.math.BigDecimal

sealed interface WeightParseResult {
    data class Valid(val tenths: Int) : WeightParseResult
    data class Invalid(val error: WeightInputError) : WeightParseResult
}

enum class WeightInputError {
    Empty,
    NotANumber,
    TooManyDecimals,
    OutOfRange,
    SaveFailed,
}

object WeightValidator {

    const val MIN_TENTHS = 200
    const val MAX_TENTHS = 3000

    private val anyDecimal = Regex("""^\d+(\.\d+)?$""")

    fun parse(input: String): WeightParseResult {
        val trimmed = input.trim()

        if (trimmed.isEmpty()) {
            return WeightParseResult.Invalid(WeightInputError.Empty)
        }
        if (!anyDecimal.matches(trimmed)) {
            return WeightParseResult.Invalid(WeightInputError.NotANumber)
        }
        if (trimmed.substringAfter('.', "").length > 1) {
            return WeightParseResult.Invalid(WeightInputError.TooManyDecimals)
        }

        val tenths = BigDecimal(trimmed).movePointRight(1).toInt()
        if (tenths !in MIN_TENTHS..MAX_TENTHS) {
            return WeightParseResult.Invalid(WeightInputError.OutOfRange)
        }
        return WeightParseResult.Valid(tenths)
    }
}
