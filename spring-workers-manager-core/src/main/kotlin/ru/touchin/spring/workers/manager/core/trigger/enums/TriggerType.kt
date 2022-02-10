package ru.touchin.spring.workers.manager.core.trigger.enums

enum class TriggerType {

    /**
     * Uses CRON expression for scheduling.
     * Expression example: `0 * * * * *`
     */
    CRON,

    /**
     * Uses numeric expressions, which mean time period in milliseconds between
     * end of previous execution and start of the next one.
     * ```
     * ------------ TIMELINE -------------------
     * [ EXECUTION ]............................
     * .............< DELAY >...................
     * ......................[ NEXT EXECUTION ]
     * -----------------------------------------
     * ```
     */
    FIXED_DELAY,

    /**
     * Uses numeric expressions, which mean time period in milliseconds between
     * start of previous execution and start of the next one.
     * ```
     * ------------ TIMELINE -------------------
     * [ EXECUTION ]............................
     * < DELAY >................................
     * .........[ NEXT EXECUTION ]..............
     * .........< DELAY >.......................
     * ..................[ 3RD EXECUTION ].....
     * -----------------------------------------
     * ```
     */
    FIXED_RATE;

    companion object {

        fun find(name: String?): TriggerType? {
            name ?: return null

            return values().find { it.name == name }
        }

    }

}
