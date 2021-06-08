@file:Suppress("unused")
package ru.touchin.wrapper.components

import org.springframework.stereotype.Component
import ru.touchin.wrapper.dto.DefaultWrapper
import ru.touchin.wrapper.dto.Wrapper

@Component
class ResponseBodyWrapperImpl : ResponseBodyWrapper {

    override fun wrap(body: Any?): Any {
        if (body is Wrapper) {
            return body
        }

        return DefaultWrapper(
            result = body,
            errorCode = 0
        )
    }

}
