package ru.touchin.spring.workers.manager.core.execution.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.findByIdOrNull
import ru.touchin.spring.workers.manager.core.execution.exceptions.ExecutionNotFoundException
import ru.touchin.spring.workers.manager.core.execution.models.ExecutionEntity
import java.util.*

interface ExecutionRepository : JpaRepository<ExecutionEntity, UUID>

fun ExecutionRepository.findByIdOrThrow(id: UUID): ExecutionEntity {
    return findByIdOrNull(id)
        ?: throw ExecutionNotFoundException(id)
}
