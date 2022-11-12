package com.cglee079.file.storage.util

import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.time.temporal.TemporalAdjusters
import java.util.*

object DateTimeUtil {

    fun LocalDateTime.toISO(): String {
        return this.format(DateTimeFormatter.ISO_DATE_TIME)
    }

    fun LocalDateTime.truncatedToMonth(): LocalDateTime {
        return this.with(TemporalAdjusters.firstDayOfMonth()).truncatedTo(ChronoUnit.DAYS)
    }

    fun LocalDateTime.toDate(): Date {
        return Date.from(this.atZone(ZoneId.systemDefault()).toInstant());
    }

    fun LocalDateTime.toMilliSecond(): Long {
        val zdt = ZonedDateTime.of(this, ZoneId.systemDefault())
        return zdt.toInstant().toEpochMilli()
    }
}
