package ru.touchin.spring.workers.manager.core.trigger.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.findByIdOrNull
import ru.touchin.spring.workers.manager.core.trigger.exceptions.TriggerNotFoundException
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import java.util.*

interface TriggerDescriptorRepository : JpaRepository<TriggerDescriptorEntity, UUID> {

    @Query(
        """
            SELECT td
            FROM TriggerDescriptorEntity td
            WHERE td.worker.workerName = :workerName
            AND td.deletedAt IS NULL
        """
    )
    fun findAll(workerName: String): List<TriggerDescriptorEntity>

}

fun TriggerDescriptorRepository.findByIdOrThrow(id: UUID): TriggerDescriptorEntity {
    return findByIdOrNull(id)
        ?: throw TriggerNotFoundException(id)
}
