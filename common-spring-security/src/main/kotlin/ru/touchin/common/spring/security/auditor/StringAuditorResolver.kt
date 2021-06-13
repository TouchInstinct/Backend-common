package ru.touchin.common.spring.security.auditor

import org.springframework.core.annotation.Order
import org.springframework.stereotype.Service
import ru.touchin.common.spring.Ordered

@Service
@Order(Ordered.HIGH)
class StringAuditorResolver : AuditorResolver {

    override fun resolve(principal: Any?): String? {
        return principal as? String
    }

}
