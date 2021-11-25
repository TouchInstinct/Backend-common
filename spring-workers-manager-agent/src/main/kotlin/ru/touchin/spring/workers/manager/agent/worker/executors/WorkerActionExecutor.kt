package ru.touchin.spring.workers.manager.agent.worker.executors

import org.quartz.Trigger
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import ru.touchin.spring.workers.manager.core.execution.dto.Execution
import ru.touchin.spring.workers.manager.core.execution.models.ExecutionEntity

interface WorkerActionExecutor {

    fun prepareExecution(job: BaseJob, currentTrigger: Trigger): Execution?
    fun finishWorkerProcessing(job: BaseJob)
    fun executeJobAction(job: BaseJob, currentTrigger: Trigger)

}
