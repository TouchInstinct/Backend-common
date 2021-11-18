package ru.touchin.spring.workers.manager.core.services.impl

import org.springframework.stereotype.Service
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.repositories.WorkerRepository
import ru.touchin.spring.workers.manager.core.services.WorkerCoreService

@Service
class WorkerCoreServiceImpl(
    private val workerRepository: WorkerRepository
) : WorkerCoreService {

    override fun save(worker: Worker): Worker {
        return workerRepository.save(worker)
    }

    override fun getByNameWithLock(workerName: String): Worker? {
        return workerRepository.findWithLock(workerName)
    }

    override fun getOrNull(name: String): Worker? = workerRepository.find(name)

    override fun getAll(): List<Worker> = workerRepository.findAll()

}
