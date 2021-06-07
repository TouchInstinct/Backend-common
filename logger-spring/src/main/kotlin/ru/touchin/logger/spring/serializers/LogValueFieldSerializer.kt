package ru.touchin.logger.spring.serializers

import ru.touchin.logger.builder.LogDataItem
import ru.touchin.logger.dto.LogValueField

interface LogValueFieldSerializer {

    operator fun invoke(field: LogValueField): List<LogDataItem>

}
