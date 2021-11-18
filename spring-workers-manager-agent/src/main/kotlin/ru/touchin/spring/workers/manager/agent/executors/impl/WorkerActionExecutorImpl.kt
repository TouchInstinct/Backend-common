package ru.touchin.spring.workers.manager.agent.executors.impl

import org.quartz.Trigger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.executors.WorkerActionExecutor
import ru.touchin.spring.workers.manager.agent.registry.TriggersRegistry
import ru.touchin.spring.workers.manager.agent.base.BaseJob
import ru.touchin.spring.workers.manager.core.models.Execution
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.services.ExecutionService
import ru.touchin.spring.workers.manager.core.services.WorkerCoreService

@Service
class WorkerActionExecutorImpl(
    private val executionService: ExecutionService,
    private val workerCoreService: WorkerCoreService,
    private val triggersRegistry: TriggersRegistry
): WorkerActionExecutor {

    @Autowired
    lateinit var workerActionExecutor: WorkerActionExecutor

    override fun executeJobAction(job: BaseJob, currentTrigger: Trigger) {
        val execution = workerActionExecutor.prepareExecution(job, currentTrigger)

        execution?.let {
            job.run()

            executionService.finishExecution(execution)
        }

        workerActionExecutor.finishWorkerProcessing(job)
    }

    @Transactional
    override fun prepareExecution(job: BaseJob, currentTrigger: Trigger): Execution? {
        val currentWorker = workerCoreService.getByNameWithLock(job.getName())

        return currentWorker
            ?.takeIf { !it.isStopped() }
            ?.let { worker ->
                val currentTriggerDescriptor = triggersRegistry.getDescriptorByTrigger(currentTrigger)

                currentTriggerDescriptor?.let {
                    setWorkerStatus(worker, WorkerStatus.PROCESSING)

                    executionService.createExecution(worker, currentTriggerDescriptor)
                }
            }
    }

    @Transactional
    override fun finishWorkerProcessing(job: BaseJob) {
        val currentWorker = workerCoreService.getByNameWithLock(job.getName())
            ?: return

        setWorkerStatus(currentWorker, WorkerStatus.IDLE)
    }

    private fun setWorkerStatus(worker: Worker, status: WorkerStatus) {
        workerCoreService.save(
            worker.also {
                it.status = status
            }
        )
    }

}
