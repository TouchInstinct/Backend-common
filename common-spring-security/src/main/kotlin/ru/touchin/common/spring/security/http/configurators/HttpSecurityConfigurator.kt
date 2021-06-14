package ru.touchin.common.spring.security.http.configurators

import org.springframework.security.config.annotation.web.builders.HttpSecurity

interface HttpSecurityConfigurator {

    fun configure(http: HttpSecurity)

}
