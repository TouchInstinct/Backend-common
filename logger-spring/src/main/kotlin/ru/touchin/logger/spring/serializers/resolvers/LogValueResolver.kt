@file:Suppress("unused")
package ru.touchin.logger.spring.serializers.resolvers

import ru.touchin.logger.spring.serializers.resolvers.dto.ResolvedValue

interface LogValueResolver<out T: Any> {

    operator fun invoke(value: Any): ResolvedValue<T>?

}
