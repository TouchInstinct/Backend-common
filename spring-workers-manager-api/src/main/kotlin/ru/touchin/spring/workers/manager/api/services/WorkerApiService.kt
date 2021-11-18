package ru.touchin.spring.workers.manager.api.services

import ru.touchin.spring.workers.manager.api.dto.WorkerDto

interface WorkerApiService {

    fun getWorker(workerName: String): WorkerDto
    fun getWorkers(): List<WorkerDto>

}
