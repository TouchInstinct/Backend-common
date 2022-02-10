package ru.touchin.spring.workers.manager.utils

import org.mockito.ArgumentMatchers
import org.mockito.Mockito

object MockitoUtils {
    fun <T> anyx(matcher: ((T) -> Boolean)? = null): T {
        @Suppress("UNCHECKED_CAST")
        return if (matcher == null) {
            ArgumentMatchers.any() ?: (null as T)
        } else {
            Mockito.argThat(matcher) ?: (null as T)
        }
    }

    fun <T> anyx(sample: T): T = anyx { it == sample }
}
