package ru.touchin.auth.security.jwt.http.configurators

import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.annotation.Order
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.oauth2.jwt.JwtDecoder
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder
import org.springframework.stereotype.Component
import ru.touchin.auth.security.jwt.properties.AccessTokenPublicProperties
import ru.touchin.common.spring.Ordered
import ru.touchin.common.spring.security.http.configurators.HttpSecurityConfigurator
import java.security.interfaces.RSAPublicKey

@Order(Ordered.HIGH)
@Component
class JwtHttpSecurityConfigurator(
    @Qualifier("accessTokenPublicKey")
    private val accessTokenPublicKey: RSAPublicKey,
    private val accessTokenProperties: AccessTokenPublicProperties
) : HttpSecurityConfigurator {

    override fun configure(http: HttpSecurity) {
        http.oauth2ResourceServer {
            it.jwt { jwt ->
                jwt.decoder(getJwtDecoder())
            }
        }
    }

    private fun getJwtDecoder(): JwtDecoder {
        return NimbusJwtDecoder.withPublicKey(accessTokenPublicKey)
            .signatureAlgorithm(accessTokenProperties.signatureAlgorithm)
            .build()
    }

}
