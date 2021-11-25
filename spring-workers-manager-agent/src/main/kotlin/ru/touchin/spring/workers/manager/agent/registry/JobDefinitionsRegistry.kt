package ru.touchin.spring.workers.manager.agent.registry

import org.quartz.JobDetail
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import ru.touchin.spring.workers.manager.agent.worker.executors.WorkerActionExecutor
import ru.touchin.spring.workers.manager.agent.quartz.RunnableJob
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob
import ru.touchin.spring.workers.manager.agent.common.utils.Glob

@Component
class JobDefinitionsRegistry(
    @Value("#{'\${workers.names}'.split(',')}")
    workersNamesPatterns: Set<String>,
    providers: List<JobProvider>,
    private val workerActionExecutor: WorkerActionExecutor
) {

    final val jobs: Map<String, BaseJob>

    private final val jobDetails: Map<String, JobDetail>

    final val jobNames: Set<String>

    init {
        val allJobs = providers.flatMap {
            val job =it.getJobs()
        job
        }

        val name2jobsList = LinkedMultiValueMap<String, BaseJob>()

        allJobs.forEach { job ->
            name2jobsList.add(job.getName(), job)
        }

        name2jobsList.forEach { (name, jobs) ->
            check(jobs.size <= 1) { "There are ${jobs.size} jobs with name '$name'. Job names must be unique" }
        }

        val name2job = name2jobsList
            .toSingleValueMap()
            .filterKeys { name -> workersNamesPatterns.any { pattern -> Glob.matches(name, pattern) } }

        jobNames = name2job.keys

        jobs = name2job

        jobDetails = name2job
            .mapValues { (_, job) ->
                createJobDetail(job)
            }
    }

    fun getJobDetail(jobName: String): JobDetail? = jobDetails[jobName]

    private fun createJobDetail(job: BaseJob): JobDetail {
        return RunnableJob
            .initJobBuilder { context ->
                workerActionExecutor.executeJobAction(job, context.trigger)
            }
            .withIdentity(job.getName())
            .build()
    }

}
