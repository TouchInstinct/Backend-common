package ru.touchin.spring.workers.manager.agent.config

import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.doReturn
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers.anyString
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.springframework.test.util.ReflectionTestUtils
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.registry.SimpleJobProvider
import ru.touchin.spring.workers.manager.agent.trigger.services.TriggerDescriptorService
import ru.touchin.spring.workers.manager.agent.worker.executors.WorkerActionExecutorImpl
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.worker.services.WorkerCoreService
import ru.touchin.spring.workers.manager.core.worker.services.WorkersStateService
import ru.touchin.spring.workers.manager.utils.MockitoUtils.anyx

class WorkerInitializerTest {

    private val simpleJobProvider = Mockito.mock(SimpleJobProvider::class.java)

    private val baseJob = Mockito.mock(BaseJob::class.java)


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

//    private val baseJob = object : BaseJob {
//        override fun run(){}
//
//        override fun getName() = BASE_WORKER_NAME
//    }

    @BeforeEach
    fun setUp() {
        doAnswer { BASE_WORKER_NAME }.`when`(baseJob).getName()
        `when`(simpleJobProvider.jobBeans).doReturn(listOf(baseJob))
        doAnswer { listOf(baseJob) }.`when`(simpleJobProvider).getJobs()

//        doAnswer { mapOf(BASE_WORKER_NAME to baseWorker) }
//            .`when`(jobDefinitionsRegistry).jobs
    }

    @Test
    fun init_existingWorker() {
        doAnswer { baseWorker }.`when`(workerCoreService).getWithLock(BASE_WORKER_NAME)
        doNothing().`when`(workersStateService).start(anyx())
//        val jobs =
        doAnswer { listOf(baseJob) }.`when`(simpleJobProvider).getJobs()


        workerInitializer.init()

        verify(workersStateService).start(baseWorker.name)
        verify(workerCoreService, never()).create(anyString())
        verify(triggerDescriptorService, never()).createDefaultTriggerDescriptors(baseWorker)
    }

    @Test
    fun init_newWorker() {
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
