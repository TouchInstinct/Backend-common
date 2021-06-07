@file:Suppress("unused")
package ru.touchin.common.spring.jpa.repositories

object RepositoryUtils {

    const val SKIP_LOCKED_PARAMETER = "javax.persistence.lock.timeout"

    /**
     * Const value which is used for 'SKIP LOCKED' in 'SELECT ... FOR UPDATE' query
     */
    const val SKIP_LOCKED_VALUE = "-2"

}
