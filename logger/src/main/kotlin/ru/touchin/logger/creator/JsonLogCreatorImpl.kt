package ru.touchin.logger.creator

import ru.touchin.logger.log.Log
import ru.touchin.logger.log.JsonLogImpl
import ru.touchin.logger.dto.LogData

class JsonLogCreatorImpl : LogCreator<LogData> {

    override fun create(clazz: Class<*>): Log<LogData> {
        return JsonLogImpl(clazz)
    }

}
