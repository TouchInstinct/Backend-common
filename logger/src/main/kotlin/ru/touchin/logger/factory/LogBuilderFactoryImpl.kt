package ru.touchin.logger.factory

import ru.touchin.logger.builder.LogBuilder
import ru.touchin.logger.builder.LogBuilderImpl
import ru.touchin.logger.creator.LogCreator
import ru.touchin.logger.dto.LogData

class LogBuilderFactoryImpl(
    private val logCreator: LogCreator<LogData>
) : LogBuilderFactory<LogData> {

    override fun create(clazz: Class<*>): LogBuilder<LogData> {
        return LogBuilderImpl(
            createLog = { logData ->
                logCreator.create(clazz)
                    .also { log ->
                        log.logData = logData
                    }
            }
        )
    }

}
