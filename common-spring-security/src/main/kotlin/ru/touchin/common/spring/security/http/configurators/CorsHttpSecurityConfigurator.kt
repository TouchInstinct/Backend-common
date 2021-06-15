package ru.touchin.common.spring.security.http.configurators

import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered

@Order(Ordered.LOW)
@Component
class CorsHttpSecurityConfigurator : HttpSecurityConfigurator {

    override fun configure(http: HttpSecurity) {
        // TODO: user properties
        http.cors().disable()
    }

}
