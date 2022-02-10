package ru.touchin.spring.workers.manager.core.worker.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import ru.touchin.spring.workers.manager.core.worker.exceptions.WorkerNotFoundException
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity
import javax.persistence.LockModeType

interface WorkerRepository : JpaRepository<WorkerEntity, String> {

    @Query(
        """
            SELECT w
            FROM WorkerEntity w
            WHERE w.workerName = :workerName
        """
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithLock(workerName: String): WorkerEntity?

    @Query(
        """
            SELECT w
            FROM WorkerEntity w
            WHERE w.workerName = :workerName
        """
    )
    fun findByName(workerName: String): WorkerEntity?

}

fun WorkerRepository.findByNameOrThrow(name: String): WorkerEntity {
    return findByName(name)
        ?: throw WorkerNotFoundException(name)
}
