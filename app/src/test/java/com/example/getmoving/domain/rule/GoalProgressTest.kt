package com.example.getmoving.domain.rule

import org.junit.Assert.assertEquals
import org.junit.Test

class GoalProgressTest {

    @Test
    fun aboveTarget_isPositive() {
        assertEquals(55, GoalProgress.deltaTenths(currentTenths = 705, targetTenths = 650))
    }

    @Test
    fun belowTarget_isNegative() {
        assertEquals(-12, GoalProgress.deltaTenths(currentTenths = 638, targetTenths = 650))
    }

    @Test
    fun reached_isZero() {
        assertEquals(0, GoalProgress.deltaTenths(currentTenths = 650, targetTenths = 650))
    }
}
