package ru.touchin.auth.security.jwks.controllers

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.auth.security.jwks.services.JwksService

@RestController
@RequestMapping("/.well-known/jwks.json")
@ConditionalOnProperty(prefix = "features", name = ["jwks"], havingValue = "true")
class JwksController(private val jwksService: JwksService) {

    @GetMapping
    fun jwks(): Map<String, Any> {
        return jwksService.get().toJSONObject()
    }

}
