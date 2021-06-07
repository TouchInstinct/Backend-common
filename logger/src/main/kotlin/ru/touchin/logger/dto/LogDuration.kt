package ru.touchin.logger.dto

import java.time.Duration

class LogDuration {

    private val startTime = Duration.ofNanos(System.nanoTime())
    private var duration: Duration? = null

    fun getDurationInMs(): Long {
        if (this.duration == null) {
            this.duration = Duration.ofNanos(System.nanoTime())
                .minus(startTime)
        }

        return this.duration!!.toMillis()
    }

}
