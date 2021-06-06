@file:Suppress("unused", "MemberVisibilityCanBePrivate")
package ru.touchin.common.date

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.*

object DateUtils {

    val MOSCOW_ZONE_ID: ZoneId = TimeZone.getTimeZone("Europe/Moscow").toZoneId()

    fun ZonedDateTime.isExpired(
        currentDate: ZonedDateTime = ZonedDateTime.now()
    ) = isBefore(currentDate)

    fun ZonedDateTime.isNotExpired(
        currentDate: ZonedDateTime = ZonedDateTime.now()
    ) = !isExpired(currentDate)

    fun ZonedDateTime.isExpired(duration: Duration) = isExpired(ZonedDateTime.now().minus(duration))

    fun ZonedDateTime.isNotExpired(duration: Duration) = !isExpired(duration)

    fun ZonedDateTime.equals(arg: ZonedDateTime, maxDiff: Duration) =
        Duration.between(this, arg) <= maxDiff

    fun LocalDateTime.isExpired(currentDate: LocalDateTime = LocalDateTime.now()) = isBefore(currentDate)

    fun LocalDateTime.isExpired(duration: Duration) = isBefore(LocalDateTime.now().minus(duration))

    fun dateMin(date1: LocalDate, date2: LocalDate?): LocalDate =
        if (date2 == null || date2.isAfter(date1)) date1 else date2

    fun LocalDate.toMoscowDateTime(): ZonedDateTime = atStartOfDay(MOSCOW_ZONE_ID)

    fun createLocalDate(): LocalDate = LocalDate.now()

    fun createLocalDateAtStartOfYear(): LocalDate = createLocalDate().withDayOfYear(1)

    fun LocalDate.startOfWeek(): LocalDate = minusDays(this.dayOfWeek.ordinal.toLong())

    fun LocalDate.endOfWeek(): LocalDate =
        plusWeeks(1L).minusDays(this.dayOfWeek.ordinal.toLong() + 1L)

}

