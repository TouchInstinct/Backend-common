package ru.touchin.auth.security.metadata.controllers

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.DefaultUriBuilderFactory
import ru.touchin.auth.security.jwt.properties.AccessTokenPublicProperties
import ru.touchin.auth.security.metadata.properties.MetadataProperties
import ru.touchin.auth.security.metadata.response.MetadataResponse
import java.net.URI

/**
 * @see <a href="https://tools.ietf.org/html/rfc8414#section-3">Obtaining Authorization Server Metadata</a>
 */
@RestController
@RequestMapping("/.well-known/oauth-authorization-server")
@ConditionalOnProperty(prefix = "features", name = ["metadata"], havingValue = "true")
class MetadataController(
    private val metadataProperties: MetadataProperties,
    private val accessTokenPublicProperties: AccessTokenPublicProperties,
) {

    @GetMapping
    fun metadata(): MetadataResponse {
        val issuer = accessTokenPublicProperties.issuer.let(URI::create)

        return MetadataResponse(
            issuer = issuer,
            tokenEndpoint = metadataProperties.tokenEndpoint?.let(issuer::withPath),
            authorizationEndpoint = issuer,
            jwksUri = issuer.withPath("/.well-known/jwks.json"),
            responseTypesSupported = metadataProperties.responseTypesSupported,
        )
    }

}

private fun URI.withPath(path: String): URI {
    return DefaultUriBuilderFactory(toString()).builder()
        .path(path)
        .build()
}

