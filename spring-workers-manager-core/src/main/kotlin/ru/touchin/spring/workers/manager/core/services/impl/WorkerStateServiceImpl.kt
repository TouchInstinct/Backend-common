package ru.touchin.spring.workers.manager.core.services.impl


import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.repositories.WorkerRepository
import ru.touchin.spring.workers.manager.core.repositories.upsert
import ru.touchin.spring.workers.manager.core.services.WorkersStateService
import java.time.ZonedDateTime

@Service
class WorkerStateServiceImpl(
    private val workerRepository: WorkerRepository
) : WorkersStateService {

    @Transactional
    override fun stop(name: String) {
        updateWorkerWithLock(name) {
            it.stoppedAt = ZonedDateTime.now()
        }
    }

    @Transactional
    override fun start(name: String) {
        updateWorkerWithLock(name) {
            it.stoppedAt = null
        }
    }

    @Transactional
    override fun disable(name: String) {
        updateWorkerWithLock(name) {
            it.disabledAt = ZonedDateTime.now()
        }
    }

    @Transactional
    override fun enable(name: String) {
        updateWorkerWithLock(name) {
            it.disabledAt = null
        }
    }

    private fun updateWorkerWithLock(name: String, updater: (Worker) -> Unit) {
        val worker = workerRepository.findWithLock(name)
            ?: throw NoSuchElementException("Worker with name $name not found")

        workerRepository.upsert(worker, updater)
    }

}
