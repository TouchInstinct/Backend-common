package ru.touchin.spring.workers.manager.agent.registry

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.touchin.spring.workers.manager.agent.common.base.BaseJob

@Component
class SimpleJobProvider : JobProvider {

    @Autowired(required = false)
    var jobBeans: List<BaseJob> = emptyList()

    override fun getJobs(): List<BaseJob> = jobBeans

}
