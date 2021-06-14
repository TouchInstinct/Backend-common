package ru.touchin.auth.core.tokens.access.dto

data class AccessTokenRequest(
    val subject: String,
    val claims: List<Pair<String, Any>>,
)
