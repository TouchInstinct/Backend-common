package ru.touchin.spring.workers.manager.core.services

import ru.touchin.spring.workers.manager.core.models.Execution
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker

interface ExecutionService{

    fun finishExecution(execution: Execution)
    fun createExecution(worker: Worker, triggerDescriptor: TriggerDescriptor?): Execution

}
