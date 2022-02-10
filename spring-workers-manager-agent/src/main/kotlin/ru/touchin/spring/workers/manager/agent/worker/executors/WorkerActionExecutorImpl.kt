package ru.touchin.spring.workers.manager.agent.worker.executors

import org.quartz.Trigger
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import ru.touchin.spring.workers.manager.agent.registry.TriggersRegistry
import ru.touchin.spring.workers.manager.core.execution.dto.Execution
import ru.touchin.spring.workers.manager.core.execution.services.ExecutionCoreService
import ru.touchin.spring.workers.manager.core.execution.services.dto.CreateExecution
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.worker.services.WorkerCoreService
import ru.touchin.spring.workers.manager.core.worker.services.dto.UpdateWorker

@Service
class WorkerActionExecutorImpl(
    private val executionCoreService: ExecutionCoreService,
    private val workerCoreService: WorkerCoreService,
    private val triggersRegistry: TriggersRegistry,
) : WorkerActionExecutor {

    @Autowired
    lateinit var workerActionExecutor: WorkerActionExecutor

    override fun executeJobAction(job: BaseJob, currentTrigger: Trigger) {
        val execution = workerActionExecutor.prepareExecution(job, currentTrigger)

        execution?.let {
            job.run()

            executionCoreService.setFinished(execution.id)
        }

        workerActionExecutor.finishWorkerProcessing(job)
    }

    @Transactional
    override fun prepareExecution(job: BaseJob, currentTrigger: Trigger): Execution? {
        val currentWorker = workerCoreService.getWithLock(job.getName())

        return currentWorker
            ?.takeIf { !it.isStopped() }
            ?.let { worker ->
                val currentTriggerDescriptor = triggersRegistry.getDescriptorByTrigger(currentTrigger)

                currentTriggerDescriptor?.let {
                    setWorkerStatus(worker.name, WorkerStatus.PROCESSING)

                    executionCoreService.create(
                        CreateExecution(
                            workerName = worker.name,
                            triggerId = currentTriggerDescriptor.id,
                        )
                    )
                }
            }
    }

    @Transactional
    override fun finishWorkerProcessing(job: BaseJob) {
        val currentWorker = workerCoreService.getWithLock(job.getName())
            ?: return

        setWorkerStatus(currentWorker.name, WorkerStatus.IDLE)
    }

    private fun setWorkerStatus(name: String, status: WorkerStatus) {
        workerCoreService.update(
            UpdateWorker(
                name = name,
                status = status,
            )
        )
    }

}
