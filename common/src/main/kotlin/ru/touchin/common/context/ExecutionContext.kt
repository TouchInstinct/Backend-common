@file:Suppress("unused")
package ru.touchin.common.context

class ExecutionContext<T>(val init: () -> T) {

    private val contextDataThreadLocal = ThreadLocal<T>()

    private fun set(executionContextData: T) {
        contextDataThreadLocal.set(executionContextData)
    }

    fun reset(): T {
        return init().also(this::set)
    }

    fun get(): T? {
        return contextDataThreadLocal.get()
    }

    fun updateContext(updater: (T) -> T): T? {
        return (get() ?: init())
            .let(updater)
            .also(this::set)
    }

}
