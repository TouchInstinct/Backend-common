package ru.touchin.spring.workers.manager.core.services

import ru.touchin.spring.workers.manager.core.models.Worker

interface WorkerCoreService{

    fun save(worker: Worker): Worker
    fun getByNameWithLock(workerName: String): Worker?
    fun get(name: String): Worker = getOrNull(name) ?: throw NoSuchElementException("Worker not found by name $name")
    fun getOrNull(name: String): Worker?
    fun getAll(): List<Worker>

}
