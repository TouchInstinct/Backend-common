package ru.touchin.spring.workers.manager.core.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor

interface TriggerDescriptorRepository : JpaRepository<TriggerDescriptor, String> {

    @Query(
        """
            SELECT td
            FROM TriggerDescriptor td
            WHERE td.worker.workerName = :workerName
            AND td.deletedAt IS NULL
        """
    )
    fun findAll(workerName: String): List<TriggerDescriptor>

}
