package ru.touchinstinct.utils.test.mockito

import org.junit.jupiter.api.fail

object AssertUtils {

    fun <T> assertContains(container: Iterable<T>, element: T) {
        if (element !in container) {
            fail("Not contains: \nElement: $element \nContainer: $container")
        }
    }

    fun <T> assertContains(container: Iterable<T>, predicate: (T) -> Boolean) {
        if (container.none(predicate)) {
            fail("Not contains: \nContainer: $container")
        }
    }

}
