package ru.touchinstinct.utils.test.mockito

import org.mockito.ArgumentMatchers
import org.mockito.ArgumentMatchers.booleanThat
import org.mockito.ArgumentMatchers.longThat
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

    fun trueBoolean(): Boolean = booleanThat { it }

    fun anyLong(matcher: ((Long) -> Boolean)? = { true }): Long = longThat(matcher)

    fun <T> anyType(type: Class<T>): T = Mockito.any<T>(type)

    inline fun <reified T> mock(clazz: Class<T> = T::class.java): T = Mockito.mock(clazz)

}
