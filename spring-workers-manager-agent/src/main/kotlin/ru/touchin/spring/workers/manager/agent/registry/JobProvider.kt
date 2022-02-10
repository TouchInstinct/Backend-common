package ru.touchin.spring.workers.manager.agent.registry

import ru.touchin.spring.workers.manager.agent.common.base.BaseJob

interface JobProvider {

    fun getJobs(): List<BaseJob>

}
