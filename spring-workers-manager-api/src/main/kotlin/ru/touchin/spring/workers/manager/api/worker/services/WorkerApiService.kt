package ru.touchin.spring.workers.manager.api.worker.services

import ru.touchin.spring.workers.manager.api.worker.controllers.dto.WorkerResponse

interface WorkerApiService {

    fun getWorker(workerName: String): WorkerResponse
    fun getWorkers(): List<WorkerResponse>

}
