package ru.touchin.spring.workers.manager.agent.executors

import org.quartz.Trigger
import ru.touchin.spring.workers.manager.agent.base.BaseJob
import ru.touchin.spring.workers.manager.core.models.Execution

interface WorkerActionExecutor {

    fun prepareExecution(job: BaseJob, currentTrigger: Trigger): Execution?
    fun finishWorkerProcessing(job: BaseJob)
    fun executeJobAction(job: BaseJob, currentTrigger: Trigger)

}
