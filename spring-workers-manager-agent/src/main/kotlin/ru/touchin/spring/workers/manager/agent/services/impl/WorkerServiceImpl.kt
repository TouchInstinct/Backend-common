package ru.touchin.spring.workers.manager.agent.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.services.WorkerService
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.models.enums.WorkerStatus
import ru.touchin.spring.workers.manager.core.repositories.WorkerRepository
import ru.touchin.spring.workers.manager.core.repositories.upsert

@Service
class WorkerServiceImpl(
    private val workerRepository: WorkerRepository
) : WorkerService {

    @Transactional
    override fun getWithLock(name: String): Worker? = workerRepository.findWithLock(name)

    override fun get(name: String): Worker? = workerRepository.find(name)

    override fun create(name: String): Worker {
        return workerRepository.upsert(Worker()) { worker ->
            worker.workerName = name
            worker.status = WorkerStatus.IDLE
        }
    }

    override fun unsetStopped(worker: Worker): Worker {
        return workerRepository.upsert(worker) { startedWorker ->
            startedWorker.stoppedAt = null
        }
    }



}
