@file:Suppress("unused")

package ru.touchin.common.measure.components

import org.springframework.boot.context.properties.ConfigurationPropertiesBinding
import org.springframework.core.convert.converter.Converter
import org.springframework.stereotype.Component
import tech.units.indriya.quantity.Quantities
import javax.measure.Quantity
import javax.measure.quantity.Length

@Component
@ConfigurationPropertiesBinding
class DistancePropertyConverter : Converter<String, Quantity<Length>> {

    override fun convert(source: String): Quantity<Length>? {
        if (source.isEmpty()) {
            return null
        }

        return Quantities.getQuantity(source).asType(Length::class.java)
    }

}
