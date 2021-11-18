package ru.touchin.spring.workers.manager.agent.scheduled

import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.Mockito.doAnswer
import org.mockito.Mockito.verify
import org.quartz.Scheduler
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.registry.TriggersRegistry
import ru.touchin.spring.workers.manager.agent.services.impl.SchedulerServiceImpl
import ru.touchin.spring.workers.manager.agent.base.BaseJob
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.TriggerType
import ru.touchin.spring.workers.manager.core.models.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.services.TriggerDescriptorCoreService

class WorkerManagerWatcherTest {
    private val testJob = Mockito.mock(BaseJob::class.java)

    private val jobDefinitionsRegistry = Mockito.mock(JobDefinitionsRegistry::class.java)
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

    private val baseWorker = Worker().also {
        it.workerName = BASE_WORKER_NAME
        it.status = WorkerStatus.IDLE
    }

    private val currentTriggerDescriptor1 = createTriggerDescriptor("curr1", TriggerType.CRON, baseWorker)
    private val actualTriggerDescriptor1 = createTriggerDescriptor("act1", TriggerType.FIXED_RATE, baseWorker)
    private val actualTriggerDescriptor2 = createTriggerDescriptor("act2", TriggerType.FIXED_RATE, baseWorker)
    private val currentTriggers = listOf(currentTriggerDescriptor1)

    @Before
    fun setUp() {
        doAnswer { currentTriggers }
            .`when`(triggerRegistry).getDescriptors()

        doAnswer { mapOf(BASE_WORKER_NAME to testJob) }
            .`when`(jobDefinitionsRegistry).jobs

        doAnswer { baseWorker.triggerDescriptors.toList() }
            .`when`(triggerDescriptorCoreService).getByWorkerName(ArgumentMatchers.anyString())
    }
    
    @Test
    fun check_onlyNewTriggersWithoutRemoving() {
        baseWorker.triggerDescriptors = setOf(currentTriggerDescriptor1, actualTriggerDescriptor1)

        workerManagerWatcher.sync()

        verify(triggerRegistry).remove(emptyList())
        verify(scheduleTriggerService).unscheduleTriggers(emptyList())

        verify(scheduleTriggerService).scheduleTriggers(listOf(actualTriggerDescriptor1))
    }

    @Test
    fun check_onlyRemoveIrrelevantTriggers() {
        baseWorker.triggerDescriptors = setOf()

        workerManagerWatcher.sync()

        verify(triggerRegistry).remove(currentTriggers)
        verify(scheduleTriggerService).unscheduleTriggers(currentTriggers)

        verify(scheduleTriggerService).scheduleTriggers(emptyList())
    }

    @Test
    fun check_saveNewAndRemoveIrrelevantTriggers() {
        baseWorker.triggerDescriptors = setOf(actualTriggerDescriptor2, actualTriggerDescriptor1)

        workerManagerWatcher.sync()

        verify(triggerRegistry).remove(currentTriggers)
        verify(scheduleTriggerService).unscheduleTriggers(currentTriggers)

        verify(scheduleTriggerService).scheduleTriggers(baseWorker.triggerDescriptors.toList())
    }

    private fun createTriggerDescriptor(id: String, type: TriggerType, worker: Worker): TriggerDescriptor {
        return TriggerDescriptor().also {
            it.id = id
            it.type = type
            it.expression = "expression"
            it.worker = worker
        }
    }

    companion object {
        private const val BASE_WORKER_NAME = "baseWorker"
    }

}
