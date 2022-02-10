package ru.touchin.spring.workers.manager.core.worker.services

import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.exceptions.WorkerNotFoundException
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity
import ru.touchin.spring.workers.manager.core.worker.services.dto.UpdateWorker

interface WorkerCoreService {

    fun create(name: String): Worker
    fun update(update: UpdateWorker): Worker
    fun getWithLock(name: String): Worker?
    fun get(name: String): Worker = getOrNull(name) ?: throw WorkerNotFoundException(name)
    fun getOrNull(name: String): Worker?
    fun getAll(): List<Worker>

}
