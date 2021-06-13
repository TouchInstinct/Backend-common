package ru.touchin.common.spring.security.configurations

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import ru.touchin.common.spring.security.url.interceptors.UrlExpressionRegistryInterceptor

@Configuration
@ComponentScan("ru.touchin.common.spring.security.url.interceptors")
@EnableGlobalMethodSecurity(prePostEnabled = true)
class DefaultSecurityConfiguration(
    private val urlExpressionRegistryInterceptors: List<UrlExpressionRegistryInterceptor>,
) : WebSecurityConfigurerAdapter() {

    override fun configure(http: HttpSecurity) {
        http
            .cors().disable()
            .csrf().disable()
            .httpBasic().disable()
            .authorizeRequests { urlExpressionRegistry ->
                urlExpressionRegistryInterceptors.forEach {
                    it.invoke(urlExpressionRegistry)
                }

                urlExpressionRegistry.anyRequest().authenticated()
            }
    }

}
