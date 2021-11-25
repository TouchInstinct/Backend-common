package ru.touchin.spring.workers.manager.core.execution.services

import ru.touchin.spring.workers.manager.core.execution.dto.Execution
import ru.touchin.spring.workers.manager.core.execution.services.dto.CreateExecution
import java.util.*

interface ExecutionCoreService{

    fun create(create: CreateExecution): Execution
    fun setFinished(id: UUID): Execution

}
