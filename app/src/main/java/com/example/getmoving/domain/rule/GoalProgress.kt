package com.example.getmoving.domain.rule

object GoalProgress {

    /** Positive means above target, negative means below target. */
    fun deltaTenths(currentTenths: Int, targetTenths: Int): Int = currentTenths - targetTenths
}
