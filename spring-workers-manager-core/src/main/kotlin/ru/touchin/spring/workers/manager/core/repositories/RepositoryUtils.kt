package ru.touchin.spring.workers.manager.core.repositories

import org.springframework.data.jpa.repository.JpaRepository

object RepositoryUtils {

    const val SKIP_LOCKED_PARAMETER = "javax.persistence.lock.timeout"


    /**
     * Const value which is used for 'SKIP LOCKED' in 'SELECT ... FOR UPDATE' query
     */
    const val SKIP_LOCKED_VALUE = "-2"

}


fun <T : Any, ID> JpaRepository<T, ID>.upsert(entity: T, updater: (T) -> Unit): T {
    return save(entity.also(updater))
}
