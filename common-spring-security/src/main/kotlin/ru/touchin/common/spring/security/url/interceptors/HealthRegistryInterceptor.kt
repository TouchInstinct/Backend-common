package ru.touchin.common.spring.security.url.interceptors

import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer
import org.springframework.stereotype.Component

@Component
class HealthRegistryInterceptor : UrlExpressionRegistryInterceptor {

    override fun invoke(
        authorizeRequestsCustomizer: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
    ) {
        authorizeRequestsCustomizer.antMatchers(HttpMethod.GET, "/api/health").permitAll()
    }

}
