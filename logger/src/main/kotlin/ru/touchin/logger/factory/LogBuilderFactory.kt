package ru.touchin.logger.factory

import ru.touchin.logger.builder.LogBuilder

interface LogBuilderFactory<T> {

    fun create(clazz: Class<*>): LogBuilder<T>

}
