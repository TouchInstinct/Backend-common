package ru.touchin.spring.workers.manager.agent.config

import org.junit.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import ru.touchin.spring.workers.manager.agent.executors.impl.WorkerActionExecutorImpl
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.services.TriggerDescriptorService
import ru.touchin.spring.workers.manager.agent.services.WorkerService
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.WorkerStatus
import ru.touchin.spring.workers.manager.utils.MockitoUtils.anyx

class WorkerInitializerTest {

    private val workerActionExecutor = Mockito.mock(WorkerActionExecutorImpl::class.java)
    private val jobDefinitionsRegistry = JobDefinitionsRegistry(setOf(BASE_WORKER_NAME), listOf(), workerActionExecutor)

    private val workerService = Mockito.mock(WorkerService::class.java)
    private val triggerDescriptorService = Mockito.mock(TriggerDescriptorService::class.java)

    private val workerInitializer = WorkerInitializer(
        triggerDescriptorService,
        jobDefinitionsRegistry,
        workerService
    )

    private val baseWorker = Worker().also {
        it.workerName = BASE_WORKER_NAME
        it.status = WorkerStatus.IDLE
    }

    @Test
    fun init_existingWorker() {
        doAnswer { baseWorker }.`when`(workerService).getWithLock(BASE_WORKER_NAME)
        doAnswer { it.arguments[0] }.`when`(workerService).unsetStopped(anyx())

        workerInitializer.init()

        verify(workerService).unsetStopped(baseWorker)
        verify(workerService, never()).create(anyString())
        verify(triggerDescriptorService, never()).createDefaultTriggerDescriptors(baseWorker)
    }

    @Test
    fun init_newWorker() {
        doAnswer { null }.`when`(workerService).getWithLock(BASE_WORKER_NAME)
        doAnswer { baseWorker }.`when`(workerService).create(BASE_WORKER_NAME)

        workerInitializer.init()

        verify(workerService).create(BASE_WORKER_NAME)
        verify(triggerDescriptorService).createDefaultTriggerDescriptors(baseWorker)
    }

    companion object {
        private const val BASE_WORKER_NAME = "baseWorker"
    }

}
