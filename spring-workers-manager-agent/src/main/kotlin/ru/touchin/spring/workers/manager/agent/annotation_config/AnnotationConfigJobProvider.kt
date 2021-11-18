package ru.touchin.spring.workers.manager.agent.annotation_config

import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.agent.base.BaseJob
import ru.touchin.spring.workers.manager.agent.registry.JobProvider

@Component
class AnnotationConfigJobProvider(
    private val jobsCollector: AnnotationConfigCollectingBeanPostProcessor
) : JobProvider {

    override fun getJobs(): List<BaseJob> = jobsCollector.jobs

}
