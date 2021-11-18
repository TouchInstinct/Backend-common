package ru.touchin.spring.workers.manager.agent.services.impl

import org.quartz.CronScheduleBuilder
import org.quartz.JobDetail
import org.quartz.ScheduleBuilder
import org.quartz.Scheduler
import org.quartz.SimpleScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder
import org.springframework.stereotype.Service
import ru.touchin.spring.workers.manager.agent.registry.JobDefinitionsRegistry
import ru.touchin.spring.workers.manager.agent.registry.TriggersRegistry
import ru.touchin.spring.workers.manager.agent.services.SchedulerService
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.enums.TriggerType

@Service
class SchedulerServiceImpl(
    private val quartzScheduler: Scheduler,
    private val jobDefinitionsRegistry: JobDefinitionsRegistry,
    private val triggersRegistry: TriggersRegistry
) : SchedulerService {

    override fun scheduleTriggers(
        triggerDescriptors: List<TriggerDescriptor>
    ) {
        triggerDescriptors.forEach(this::scheduleTrigger)
    }

    override fun unscheduleTriggers(
        triggerDescriptors: List<TriggerDescriptor>
    ) {
        triggerDescriptors.forEach { descriptor ->
            val trigger = triggersRegistry.getTriggerByDescriptor(descriptor)

            if (trigger != null) {
                quartzScheduler.unscheduleJob(trigger.key)
            }
        }
    }

    private fun scheduleTrigger(descriptor: TriggerDescriptor) {
        val jobDetail = jobDefinitionsRegistry.getJobDetail(descriptor.worker.workerName)

        if (jobDetail != null) {
            val trigger = createTrigger(jobDetail, descriptor)

            triggersRegistry.putTrigger(descriptor, trigger)

            if (!quartzScheduler.checkExists(trigger.key)) {
                if (quartzScheduler.checkExists(jobDetail.key)) {
                    quartzScheduler.scheduleJob(trigger)
                } else {
                    quartzScheduler.scheduleJob(jobDetail, trigger)
                }
            }
        }
    }

    private fun createTrigger(job: JobDetail, triggerDescriptor: TriggerDescriptor): Trigger {
        return TriggerBuilder.newTrigger().forJob(job)
            .withIdentity(createTriggerName(job, triggerDescriptor))
            .withSchedule(getScheduleBuilder(triggerDescriptor))
            .build()
    }

    private fun createTriggerName(job: JobDetail, triggerDescriptor: TriggerDescriptor) =
        "${job.key.name}_${triggerDescriptor.id}_trigger"

    private fun getScheduleBuilder(triggerDescriptor: TriggerDescriptor): ScheduleBuilder<out Trigger> {
        return when (triggerDescriptor.type) {
            TriggerType.CRON -> cronSchedule(triggerDescriptor.expression)
            TriggerType.FIXED_RATE -> fixedRateSchedule(triggerDescriptor.expression.toLong())
            TriggerType.FIXED_DELAY -> fixedRateSchedule(triggerDescriptor.expression.toLong())
        }
    }

    private fun fixedRateSchedule(interval: Long): SimpleScheduleBuilder {
        return SimpleScheduleBuilder.simpleSchedule().repeatForever().withIntervalInMilliseconds(interval)
    }

    private fun cronSchedule(expression: String): CronScheduleBuilder {
        return CronScheduleBuilder.cronSchedule(expression)
    }

}
