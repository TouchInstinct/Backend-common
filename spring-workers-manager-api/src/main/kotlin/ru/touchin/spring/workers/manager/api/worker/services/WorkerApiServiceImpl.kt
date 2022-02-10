package ru.touchin.spring.workers.manager.api.worker.services

import org.springframework.stereotype.Service
import ru.touchin.spring.workers.manager.api.worker.controllers.dto.WorkerResponse
import ru.touchin.spring.workers.manager.api.worker.controllers.dto.toWorkerResponse
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.services.WorkerCoreService

@Service
class WorkerApiServiceImpl(
    private val workerCoreService: WorkerCoreService
) : WorkerApiService {

    override fun getWorker(workerName: String): WorkerResponse {
        return workerCoreService.get(workerName).toWorkerResponse()
    }

    override fun getWorkers(): List<WorkerResponse> {
        return workerCoreService.getAll().map(Worker::toWorkerResponse)
    }

}
