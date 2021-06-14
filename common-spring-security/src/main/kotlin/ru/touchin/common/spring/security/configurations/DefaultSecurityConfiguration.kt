package ru.touchin.common.spring.security.configurations

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import ru.touchin.common.spring.security.http.configurators.HttpSecurityConfigurator

@Configuration
@ComponentScan(
    "ru.touchin.common.spring.security.url.interceptors",
    "ru.touchin.common.spring.security.http.configurators",
)
@EnableGlobalMethodSecurity(prePostEnabled = true)
class DefaultSecurityConfiguration(
    private val httpSecurityConfigurators: List<HttpSecurityConfigurator>,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
            httpSecurityConfigurators.forEach {
                it.configure(http)
            }
    }

}
