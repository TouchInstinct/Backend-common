package ru.touchin.auth.security.jwks.services

import com.nimbusds.jose.jwk.JWKSet
import com.nimbusds.jose.jwk.KeyUse
import com.nimbusds.jose.jwk.RSAKey
import org.springframework.stereotype.Service
import java.security.interfaces.RSAPublicKey
import java.util.UUID

@Service
class JwksServiceImpl(private val rsaPublicKey: RSAPublicKey) : JwksService {

    private val keyId = UUID.randomUUID().toString()

    override fun get(): JWKSet {
        val jwk = RSAKey.Builder(rsaPublicKey)
            .keyUse(KeyUse.SIGNATURE)
            .keyID(keyId)
            .build()

        return JWKSet(jwk)
    }

}
