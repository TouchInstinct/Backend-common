package ru.touchin.common.spring.security.url.interceptors

import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer

interface UrlExpressionRegistryInterceptor {

    operator fun invoke(
        authorizeRequestsCustomizer: ExpressionUrlAuthorizationConfigurer<HttpSecurity>.ExpressionInterceptUrlRegistry
    )

}
