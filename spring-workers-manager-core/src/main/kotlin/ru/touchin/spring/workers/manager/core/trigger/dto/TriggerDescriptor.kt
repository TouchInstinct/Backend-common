package ru.touchin.spring.workers.manager.core.trigger.dto

import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType
import java.time.ZonedDateTime
import java.util.*

data class TriggerDescriptor(
    val id: UUID,
    val name: String,
    val type: TriggerType,
    val expression: String,
    val workerName: String,
    val disabledAt: ZonedDateTime?,
    val deletedAt: ZonedDateTime?,
) {

    fun isDeleted() = deletedAt != null

    fun isDisabled() = disabledAt != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TriggerDescriptor) return false

        if (id != other.id || expression != other.expression || type != other.type) {
            return false
        }

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
