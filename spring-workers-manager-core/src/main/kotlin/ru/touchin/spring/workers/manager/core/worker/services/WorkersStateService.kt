package ru.touchin.spring.workers.manager.core.worker.services

interface WorkersStateService {

    fun stop(name: String)
    fun start(name: String)
    fun disable(name: String)
    fun enable(name: String)

}
