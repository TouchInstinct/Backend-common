package ru.touchin.spring.workers.manager.core.worker.services

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.core.worker.converters.toWorker
import ru.touchin.spring.workers.manager.core.worker.dto.Worker
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity
import ru.touchin.spring.workers.manager.core.worker.repositories.WorkerRepository
import ru.touchin.spring.workers.manager.core.worker.repositories.findByNameOrThrow
import ru.touchin.spring.workers.manager.core.worker.services.dto.UpdateWorker

@Service
class WorkerCoreServiceImpl(
    private val workerRepository: WorkerRepository,
) : WorkerCoreService {

    @Transactional
    override fun create(name: String): Worker {
        val entity = WorkerEntity().apply {
            workerName = name
            status = WorkerStatus.IDLE
        }

        return workerRepository.save(entity)
            .toWorker()
    }

    @Transactional
    override fun update(update: UpdateWorker): Worker {
        val entity = workerRepository.findByNameOrThrow(update.name)
            .apply { status = update.status }

        return workerRepository.save(entity)
            .toWorker()
    }

    @Transactional
    override fun getWithLock(name: String): Worker? {
        return workerRepository.findWithLock(name)
            ?.toWorker()
    }

    @Transactional(readOnly = true)
    override fun getOrNull(name: String): Worker? {
        return workerRepository.findByName(name)
            ?.toWorker()
    }

    @Transactional(readOnly = true)
    override fun getAll(): List<Worker> {
        return workerRepository.findAll()
            .map(WorkerEntity::toWorker)
    }

}
