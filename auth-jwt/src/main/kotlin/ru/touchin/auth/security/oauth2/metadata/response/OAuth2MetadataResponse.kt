package ru.touchin.auth.security.oauth2.metadata.response

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import java.net.URI

/**
 * @see <a href="https://tools.ietf.org/html/rfc8414#section-3.2">Authorization Server Metadata Response</a>
 */
data class OAuth2MetadataResponse(
    val issuer: URI,
    @JsonProperty("token_endpoint")
    @JsonInclude(JsonInclude.Include.NON_NULL)
    val tokenEndpoint: URI?,
    // authorization_endpoint is only included for compatibility with
    // the spring oauth client registration implementation, it is neither
    // supported by this server nor required according to the specification
    @JsonProperty("authorization_endpoint")
    val authorizationEndpoint: URI,
    @JsonProperty("jwks_uri")
    val jwksUri: URI,
    @JsonProperty("response_types_supported")
    val responseTypesSupported: List<String>
)
