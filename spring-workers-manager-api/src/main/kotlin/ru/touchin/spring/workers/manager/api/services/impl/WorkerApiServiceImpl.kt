package ru.touchin.spring.workers.manager.api.services.impl

import org.springframework.stereotype.Service
import ru.touchin.spring.workers.manager.api.dto.WorkerDto
import ru.touchin.spring.workers.manager.api.dto.toDto
import ru.touchin.spring.workers.manager.api.services.WorkerApiService
import ru.touchin.spring.workers.manager.core.services.WorkerCoreService

@Service
class WorkerApiServiceImpl(
    private val workerCoreService: WorkerCoreService
) : WorkerApiService {

    override fun getWorker(workerName: String): WorkerDto {
        return workerCoreService.get(workerName).toDto()
    }

    override fun getWorkers(): List<WorkerDto> {
        return workerCoreService.getAll().map { it.toDto() }
    }

}
