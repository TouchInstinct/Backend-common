package ru.touchin.spring.workers.manager.core.worker.services


import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity
import ru.touchin.spring.workers.manager.core.worker.repositories.WorkerRepository
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

    private fun updateWorkerWithLock(name: String, updater: (WorkerEntity) -> Unit) {
        val worker = workerRepository.findWithLock(name)
            ?.apply(updater)
            ?: throw NoSuchElementException("Worker with name $name not found")

        workerRepository.save(worker)
    }

}
