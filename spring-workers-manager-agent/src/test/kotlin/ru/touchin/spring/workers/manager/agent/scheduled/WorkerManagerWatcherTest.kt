package ru.touchin.spring.workers.manager.agent.scheduled

import com.nhaarman.mockitokotlin2.mock
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.anyString
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.verify
import org.quartz.Scheduler
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.registry.JobProvider
import ru.touchin.spring.workers.manager.agent.registry.TriggersRegistry
import ru.touchin.spring.workers.manager.agent.scheduled.services.SchedulerServiceImpl
import ru.touchin.spring.workers.manager.agent.worker.executors.WorkerActionExecutorImpl
import ru.touchin.spring.workers.manager.core.trigger.dto.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType
import ru.touchin.spring.workers.manager.core.trigger.services.TriggerDescriptorCoreService
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import java.util.*

internal class WorkerManagerWatcherTest {

    private val baseJob = mock<BaseJob> {
        on(it.getName()).thenReturn(BASE_WORKER_NAME)
    }

    private val simpleJobProvider = mock<JobProvider> {
        on(it.getJobs()).thenReturn(listOf(baseJob))
    }

    private val workerActionExecutor = Mockito.mock(WorkerActionExecutorImpl::class.java)

    private val jobDefinitionsRegistry = JobDefinitionsRegistry(setOf(BASE_WORKER_NAME), listOf(simpleJobProvider), workerActionExecutor)

    private val triggerRegistry = Mockito.mock(TriggersRegistry::class.java)
    private val watcherSyncInterval = 1000L

    private val triggerDescriptorCoreService = Mockito.mock(TriggerDescriptorCoreService::class.java)
    private val scheduleTriggerService = Mockito.mock(SchedulerServiceImpl::class.java)
    private val quartzScheduler = Mockito.mock(Scheduler::class.java)

    private val workerManagerWatcher = WorkerManagerWatcher(
        watcherSyncInterval,
        jobDefinitionsRegistry,
        scheduleTriggerService,
        triggerRegistry,
        triggerDescriptorCoreService,
        quartzScheduler
    )

    private val baseWorker = Worker(
        name = BASE_WORKER_NAME,
        status = WorkerStatus.IDLE,
        disabledAt = null,
        stoppedAt = null,
        parallelExecutionEnabled = false,
    )

    private val triggerDescriptorId1 = UUID.fromString("514fb14b-d7ea-4ace-b200-4e06e80c37b7")
    private val triggerDescriptorId2 = UUID.fromString("94d2ef98-6fc9-4d41-a66a-35b73a2448e0")
    private val triggerDescriptorId3 = UUID.fromString("2a7719ec-6a65-481e-947b-08537ada2337")
    private val currentTriggerDescriptor1 = createTriggerDescriptor(triggerDescriptorId1, TriggerType.CRON, baseWorker)
    private val actualTriggerDescriptor1 = createTriggerDescriptor(triggerDescriptorId2, TriggerType.FIXED_RATE, baseWorker)
    private val actualTriggerDescriptor2 = createTriggerDescriptor(triggerDescriptorId3, TriggerType.FIXED_RATE, baseWorker)
    private val currentTriggers = listOf(currentTriggerDescriptor1)

    @BeforeEach
    fun setUp() {
        doAnswer { currentTriggers }
            .`when`(triggerRegistry).getDescriptors()
    }

    @Test
    fun checkSyncWithOnlyNewTriggersWithoutRemoving() {
        doAnswer { listOf(currentTriggerDescriptor1, actualTriggerDescriptor1) }
            .`when`(triggerDescriptorCoreService).getByWorkerName(anyString())

        workerManagerWatcher.sync()

        verify(triggerRegistry).remove(emptyList())
        verify(scheduleTriggerService).unscheduleTriggers(emptyList())

        verify(scheduleTriggerService).scheduleTriggers(listOf(actualTriggerDescriptor1))
    }

    @Test
    fun checkSyncWithOnlyRemoveIrrelevantTriggers() {
        doAnswer { emptyList<TriggerDescriptor>() }
            .`when`(triggerDescriptorCoreService).getByWorkerName(ArgumentMatchers.anyString())

        workerManagerWatcher.sync()

        verify(triggerRegistry).remove(currentTriggers)
        verify(scheduleTriggerService).unscheduleTriggers(currentTriggers)

        verify(scheduleTriggerService).scheduleTriggers(emptyList())
    }

    @Test
    fun checkSyncWithSaveNewAndRemoveIrrelevantTriggers() {
        doAnswer { listOf(actualTriggerDescriptor2, actualTriggerDescriptor1) }
            .`when`(triggerDescriptorCoreService).getByWorkerName(ArgumentMatchers.anyString())

        workerManagerWatcher.sync()

        verify(triggerRegistry).remove(currentTriggers)
        verify(scheduleTriggerService).unscheduleTriggers(currentTriggers)

        verify(scheduleTriggerService).scheduleTriggers(setOf(actualTriggerDescriptor2, actualTriggerDescriptor1).toList())
    }

    private fun createTriggerDescriptor(id: UUID, type: TriggerType, worker: Worker): TriggerDescriptor {
        return TriggerDescriptor(
            id = id,
            name = id.toString(),
            type = type,
            expression = "expression",
            workerName = worker.name,
            disabledAt = null,
            deletedAt = null,
        )
    }

    companion object {

        private const val BASE_WORKER_NAME = "baseWorker"

    }

}
