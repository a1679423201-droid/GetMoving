package com.example.getmoving.ui.format

import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale
import kotlin.math.abs

object WeightFormatter {

    fun formatKg(tenths: Int): String = String.format(Locale.getDefault(), "%.1f kg", tenths / 10.0)

    /** 纯数值，用于输入框预填；固定小数点，保证能被 WeightValidator 解析。 */
    fun formatPlainTenths(tenths: Int): String = String.format(Locale.US, "%.1f", tenths / 10.0)

    fun formatDeltaKg(tenths: Int): String =
        String.format(Locale.getDefault(), "%.1f kg", abs(tenths) / 10.0)
}

object DateFormatter {

    private val fullDate = DateTimeFormatter.ofPattern("M月d日 EEEE", Locale.CHINA)
    private val shortDate = DateTimeFormatter.ofPattern("MM-dd", Locale.CHINA)

    fun full(date: LocalDate): String = date.format(fullDate)

    fun short(date: LocalDate): String = date.format(shortDate)
}
