package com.example.getmoving.domain.rule

import org.junit.Assert.assertEquals
import org.junit.Test

class WeightValidatorTest {

    @Test
    fun parse_acceptsInteger() {
        assertEquals(WeightParseResult.Valid(700), WeightValidator.parse("70"))
    }

    @Test
    fun parse_acceptsOneDecimal() {
        assertEquals(WeightParseResult.Valid(705), WeightValidator.parse("70.5"))
    }

    @Test
    fun parse_acceptsBoundaries() {
        assertEquals(WeightParseResult.Valid(200), WeightValidator.parse("20.0"))
        assertEquals(WeightParseResult.Valid(3000), WeightValidator.parse("300.0"))
    }

    @Test
    fun empty_rejected() {
        assertInvalid(WeightInputError.Empty, "")
        assertInvalid(WeightInputError.Empty, "   ")
    }

    @Test
    fun notANumber_rejected() {
        assertInvalid(WeightInputError.NotANumber, "abc")
        assertInvalid(WeightInputError.NotANumber, ".5")
        assertInvalid(WeightInputError.NotANumber, "70.")
    }

    @Test
    fun moreThanOneDecimal_rejected() {
        assertInvalid(WeightInputError.TooManyDecimals, "70.55")
    }

    @Test
    fun outOfRange_rejected() {
        assertInvalid(WeightInputError.OutOfRange, "19.9")
        assertInvalid(WeightInputError.OutOfRange, "300.1")
    }

    private fun assertInvalid(expected: WeightInputError, input: String) {
        val result = WeightValidator.parse(input)
        assertEquals(WeightParseResult.Invalid(expected), result)
    }
}
