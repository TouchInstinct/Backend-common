package ru.touchin.spring.workers.manager.agent.services

import ru.touchin.spring.workers.manager.core.models.Worker

interface WorkerService {

    fun get(name: String): Worker?
    fun getWithLock(name: String): Worker?
    fun create(name: String): Worker
    fun unsetStopped(worker: Worker): Worker

}
