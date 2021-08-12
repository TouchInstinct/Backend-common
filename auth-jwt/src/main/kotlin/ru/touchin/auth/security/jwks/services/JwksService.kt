package ru.touchin.auth.security.jwks.services

import com.nimbusds.jose.jwk.JWKSet

interface JwksService {

    fun get(): JWKSet

}
