package ru.touchin.auth.security.oauth2.metadata.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.util.DefaultUriBuilderFactory
import ru.touchin.auth.core.tokens.access.properties.AccessTokenProperties
import ru.touchin.auth.security.oauth2.metadata.properties.OAuth2MetadataProperties
import ru.touchin.auth.security.oauth2.metadata.response.OAuth2MetadataResponse
import java.net.URI

/**
 * @see <a href="https://tools.ietf.org/html/rfc8414#section-3">Obtaining Authorization Server Metadata</a>
 */
@RestController
@RequestMapping("/.well-known/oauth-authorization-server")
class OAuth2MetadataController(
    private val accessTokenProperties: AccessTokenProperties,
    private val oauth2MetadataProperties: OAuth2MetadataProperties,
) {

    @GetMapping
    fun metadata(): OAuth2MetadataResponse {
        val issuer = accessTokenProperties.issuer.let(URI::create)

        return OAuth2MetadataResponse(
            issuer = issuer,
            tokenEndpoint = oauth2MetadataProperties.tokenEndpoint?.let(issuer::withPath),
            authorizationEndpoint = issuer,
            jwksUri = issuer.withPath("/.well-known/jwks.json"),
            responseTypesSupported = oauth2MetadataProperties.responseTypesSupported,
        )
    }

}

private fun URI.withPath(path: String): URI {
    return DefaultUriBuilderFactory(toString()).builder()
        .path(path)
        .build()
}

