package ru.touchin.common.spring.security.http.configurators

import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.stereotype.Component
import ru.touchin.common.spring.Ordered
import ru.touchin.common.spring.security.url.interceptors.UrlExpressionRegistryInterceptor

@Order(Ordered.NORMAL)
@Component
class AuthorizeRequestsHttpSecurityConfigurator(
    private val urlExpressionRegistryInterceptors: List<UrlExpressionRegistryInterceptor>,
) : HttpSecurityConfigurator {

    override fun configure(http: HttpSecurity) {
        http.authorizeRequests { urlExpressionRegistry ->
            urlExpressionRegistryInterceptors.forEach {
                it.invoke(urlExpressionRegistry)
            }

            urlExpressionRegistry.anyRequest().authenticated()
        }
    }

}
