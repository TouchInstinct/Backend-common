package ru.touchinstinct.utils

import java.time.Duration
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.util.TimeZone

val MOSCOW_ZONE_ID: ZoneId = TimeZone.getTimeZone("Europe/Moscow").toZoneId()

fun ZonedDateTime.isExpired(duration: Duration) = this.isExpired(ZonedDateTime.now().minus(duration))

fun ZonedDateTime.isExpired(currentDate: ZonedDateTime = ZonedDateTime.now()) = this.isBefore(currentDate)

fun ZonedDateTime.isNotExpired(duration: Duration) = !this.isExpired(duration)

fun ZonedDateTime.isNotExpired(currentDate: ZonedDateTime = ZonedDateTime.now()) = !this.isExpired(currentDate)

fun minusYearsAndGetFirstDayOfYear(numberOfYears: Long): ZonedDateTime =
    ZonedDateTime.now().minusYears(numberOfYears).withDayOfYear(1)

fun ZonedDateTime.equals(arg: ZonedDateTime, maxDiff: Duration) =
    Duration.between(this, arg) <= maxDiff

fun LocalDateTime.isExpired(currentDate: LocalDateTime = LocalDateTime.now()) = this.isBefore(currentDate)

fun LocalDateTime.isExpired(duration: Duration) = this.isBefore(LocalDateTime.now().minus(duration))

fun dateMin(date1: LocalDate, date2: LocalDate?): LocalDate =
    if (date2 == null || date2.isAfter(date1)) date1 else date2

fun LocalDate.toZonedDateTime(): ZonedDateTime = atStartOfDay(MOSCOW_ZONE_ID)

fun createLocalDate(): LocalDate = LocalDate.now()

fun createLocalDateAtStartOfYear(): LocalDate = createLocalDate().withDayOfYear(1)

fun LocalDate.startOfWeek(): LocalDate = this.minusDays(this.dayOfWeek.ordinal.toLong())

fun LocalDate.endOfWeek(): LocalDate = this.plusWeeks(1L).minusDays(this.dayOfWeek.ordinal.toLong() + 1L)
