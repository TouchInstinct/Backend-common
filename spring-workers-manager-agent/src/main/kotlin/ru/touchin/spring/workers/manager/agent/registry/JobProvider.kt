package ru.touchin.spring.workers.manager.agent.registry

import ru.touchin.spring.workers.manager.agent.base.BaseJob

interface JobProvider {
    fun getJobs(): List<BaseJob>
}
