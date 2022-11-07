package ru.touchin.push.message.provider.fcm.converters

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import java.text.SimpleDateFormat
import java.util.*

@ConfigurationPropertiesBinding
@Component
class DateConverter(
    @Qualifier("push-message-provider.fcm.auth")
    private val simpleDateFormat: SimpleDateFormat
) : Converter<String, Date> {

    override fun convert(source: String): Date {
        return source.let(simpleDateFormat::parse)
    }

}
