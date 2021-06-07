package ru.touchin.logger.creator

import ru.touchin.logger.log.Log
import ru.touchin.logger.log.SimpleLogImpl
import ru.touchin.logger.dto.LogData

class SimpleLogCreatorImpl : LogCreator<LogData> {

    override fun create(clazz: Class<*>): Log<LogData> {
        return SimpleLogImpl(clazz)
    }

}
