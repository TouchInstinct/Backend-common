package ru.touchin.auth.security.jwt.http.configurators

import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.common.spring.security.http.configurators.HttpSecurityConfigurator

@Order(Ordered.HIGH)
@Component
class JwtHttpSecurityConfigurator : HttpSecurityConfigurator {

    override fun configure(http: HttpSecurity) {
        http.oauth2ResourceServer {
            it.jwt()
        }
    }

}
