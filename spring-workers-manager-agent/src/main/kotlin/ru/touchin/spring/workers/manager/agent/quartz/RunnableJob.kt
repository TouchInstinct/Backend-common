package ru.touchin.spring.workers.manager.agent.quartz

import org.quartz.Job
import org.quartz.JobBuilder
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import org.quartz.JobExecutionException

typealias JobFunction = (JobExecutionContext) -> Unit

class RunnableJob : Job {

    override fun execute(context: JobExecutionContext) {
        try {
            @Suppress("UNCHECKED_CAST")
            (context.jobDetail.jobDataMap[ACTION] as JobFunction).also { action ->
                action.invoke(context)
            }
        } catch (e: Exception) {
            throw JobExecutionException(e)
        }
    }

    companion object {

        private const val ACTION = "ACTION"

        fun initJobBuilder(action: JobFunction): JobBuilder {
            return JobBuilder
                .newJob(RunnableJob::class.java)
                .usingJobData(JobDataMap(mapOf(ACTION to action)))
        }

    }

}
