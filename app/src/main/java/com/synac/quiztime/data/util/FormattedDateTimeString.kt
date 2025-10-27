package com.synac.quiztime.data.util


import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
fun Long.toFormattedDateTimeString(): String {
    val instant = Instant.fromEpochMilliseconds(epochMilliseconds = this)
    val localDateTime = instant.toLocalDateTime(TimeZone.currentSystemDefault())
    return "${localDateTime.date} ${localDateTime.time.toString().substring(0, 8)}"
}