package ru.touchin.spring.workers.manager.core.trigger.dto

import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
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

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TriggerDescriptorEntity) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

}
