package ru.touchin.spring.workers.manager.agent.scheduled

import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.TriggerBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.agent.quartz.RunnableJob
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.services.SchedulerService
import ru.touchin.spring.workers.manager.agent.registry.TriggersRegistry
import ru.touchin.spring.workers.manager.core.services.TriggerDescriptorCoreService

/**
 * Class is proceeding regular synchronisation trigger descriptors from db and quartz scheduled triggers
 */
@Component
class WorkerManagerWatcher(
    @Value("\${workers.watcher.sync.interval}")
    private val watcherSyncInterval: Long,
    private val jobDefinitionsRegistry: JobDefinitionsRegistry,
    private val scheduleTriggerService: SchedulerService,
    private val triggersRegistry: TriggersRegistry,
    private val triggerDescriptorCoreService: TriggerDescriptorCoreService,
    private val quartzScheduler: Scheduler
) {

    fun init() {
        val systemJobDetail = RunnableJob
            .initJobBuilder { sync() }
            .withIdentity(SYSTEM_JOB_NAME, SYSTEM_JOB_GROUP)
            .build()

        val systemTrigger = TriggerBuilder.newTrigger()
            .forJob(systemJobDetail)
            .withIdentity("${SYSTEM_JOB_NAME}_trigger")
            .withSchedule(SimpleScheduleBuilder
                .simpleSchedule()
                .repeatForever()
                .withIntervalInMilliseconds(watcherSyncInterval)
            )
            .build()

        quartzScheduler.scheduleJob(systemJobDetail, systemTrigger)
    }

    fun sync() {
        val currentTriggerDescriptors = triggersRegistry.getDescriptors()

        val actualTriggerDescriptors = jobDefinitionsRegistry.jobs
            .flatMap { (jobName, _) -> triggerDescriptorCoreService.getByWorkerName(jobName) }
            .filter { !it.isDisabled() }

        val deletedTriggerDescriptors = currentTriggerDescriptors - actualTriggerDescriptors
        scheduleTriggerService.unscheduleTriggers(deletedTriggerDescriptors)
        triggersRegistry.remove(deletedTriggerDescriptors)

        val newTriggerDescriptors = actualTriggerDescriptors - currentTriggerDescriptors
        scheduleTriggerService.scheduleTriggers(newTriggerDescriptors)
    }

    companion object {
        private const val SYSTEM_JOB_GROUP = "SYSTEM"
        private const val SYSTEM_JOB_NAME = "system_worker_check"
    }

}
