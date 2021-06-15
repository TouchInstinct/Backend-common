package ru.touchin.auth.core.tokens.access.services

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTCreator
import com.auth0.jwt.algorithms.Algorithm
import org.springframework.stereotype.Service
import ru.touchin.auth.core.tokens.access.dto.AccessToken
import ru.touchin.auth.core.tokens.access.dto.AccessTokenRequest
import ru.touchin.auth.core.tokens.access.exceptions.UnsupportedClaimType
import ru.touchin.auth.core.tokens.access.properties.AccessTokenProperties
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtAccessTokenCoreServiceImpl(
    private val accessTokenProperties: AccessTokenProperties,
    private val accessTokenSigningAlgorithm: Algorithm
) : AccessTokenCoreService {

    private fun sign(builder: JWTCreator.Builder) = builder.sign(accessTokenSigningAlgorithm)

    private fun getExpirationDate(): Date {
        return LocalDateTime.now()
            .plus(accessTokenProperties.timeToLive)
            .atZone(ZoneId.systemDefault())
            .toInstant()
            .let(Date::from)
    }

    override fun create(accessTokenRequest: AccessTokenRequest): AccessToken {
        val token = JWT.create()
            .withIssuer(accessTokenProperties.issuer)
            .withExpiresAt(getExpirationDate())
            .withSubject(accessTokenRequest.subject)
            .apply {
                accessTokenRequest.claims.forEach { (name, value) ->
                    when(value) {
                        is String -> withClaim(name, value)
                        is Int -> withClaim(name, value)
                        else -> throw UnsupportedClaimType(value::class.simpleName)
                    }
                }
            }
            .let(this::sign)

        return AccessToken(token, accessTokenProperties.timeToLive)
    }

}
