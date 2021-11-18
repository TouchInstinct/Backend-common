package ru.touchin.spring.workers.manager.core.repositories

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.jpa.repository.QueryHints
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.repositories.RepositoryUtils.SKIP_LOCKED_PARAMETER
import ru.touchin.spring.workers.manager.core.repositories.RepositoryUtils.SKIP_LOCKED_VALUE
import javax.persistence.LockModeType
import javax.persistence.QueryHint

interface WorkerRepository : JpaRepository<Worker, String> {

    @Query(
        """
            SELECT w
            FROM Worker w
            WHERE w.workerName = :workerName
        """
    )
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    fun findWithLock(workerName: String): Worker?

    @Query(
        """
            SELECT w
            FROM Worker w
            WHERE w.workerName = :workerName
        """
    )
    fun find(workerName: String): Worker?

}
