package ru.touchin.spring.workers.manager.agent.config

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.registry.JobProvider
import ru.touchin.spring.workers.manager.agent.trigger.services.TriggerDescriptorService
import ru.touchin.spring.workers.manager.agent.worker.executors.WorkerActionExecutorImpl
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.worker.services.WorkerCoreService
import ru.touchin.spring.workers.manager.core.worker.services.WorkersStateService
import ru.touchin.spring.workers.manager.utils.MockitoUtils.anyx

internal class WorkerInitializerTest {

    private val baseJob = mock<BaseJob> {
        on(it.getName()).thenReturn(BASE_WORKER_NAME)
    }

    private val simpleJobProvider = mock<JobProvider> {
        on(it.getJobs()).thenReturn(listOf(baseJob))
    }

    private val workerActionExecutor = Mockito.mock(WorkerActionExecutorImpl::class.java)

    private val jobDefinitionsRegistry = JobDefinitionsRegistry(setOf(BASE_WORKER_NAME), listOf(simpleJobProvider), workerActionExecutor)
    private val workerCoreService = Mockito.mock(WorkerCoreService::class.java)

    private val workersStateService = Mockito.mock(WorkersStateService::class.java)

    private val triggerDescriptorService = Mockito.mock(TriggerDescriptorService::class.java)

    private val workerInitializer = WorkerInitializer(
        triggerDescriptorService,
        jobDefinitionsRegistry,
        workerCoreService,
        workersStateService,
    )

    private val baseWorker = Worker(
        name = BASE_WORKER_NAME,
        status = WorkerStatus.IDLE,
        disabledAt = null,
        stoppedAt = null,
        parallelExecutionEnabled = false,
    )

    @Test
    fun checkSyncWithExistingWorker() {
        doAnswer { baseWorker }.`when`(workerCoreService).getWithLock(BASE_WORKER_NAME)
        doNothing().`when`(workersStateService).start(anyx())

        workerInitializer.init()

        verify(workersStateService).start(baseWorker.name)
        verify(workerCoreService, never()).create(anyString())
        verify(triggerDescriptorService, never()).createDefaultTriggerDescriptors(baseWorker)
    }

    @Test
    fun checkSyncAndCreateNewWorker() {
        doAnswer { null }.`when`(workerCoreService).getWithLock(BASE_WORKER_NAME)
        doAnswer { baseWorker }.`when`(workerCoreService).create(BASE_WORKER_NAME)

        workerInitializer.init()

        verify(workerCoreService).create(BASE_WORKER_NAME)
        verify(triggerDescriptorService).createDefaultTriggerDescriptors(baseWorker)
    }

    companion object {

        private const val BASE_WORKER_NAME = "baseWorker"

    }

}
