package ru.touchin.logger.creator

import ru.touchin.logger.log.Log

interface LogCreator<T> {

    fun create(clazz: Class<*>): Log<T>

}
