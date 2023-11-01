package com.quid.auth.global.token.domain

import io.jsonwebtoken.Claims
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class Payload(
    val jti: String = UUID.randomUUID().toString(),
    val iss: String = "QUID_LAB",
    val sub: String,
    val iat: LocalDateTime = LocalDateTime.now(),
    val exp: LocalDateTime,
    val username: String,
) {
    constructor(claims: Claims) : this(
        jti = claims.id,
        iss = claims.issuer,
        sub = claims.subject,
        iat = claims.issuedAt.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
        exp = claims.expiration.toInstant().atOffset(ZoneOffset.UTC).toLocalDateTime(),
        username = claims["username"] as String
    )

    init {
        require(exp.isAfter(iat)) { "토큰 만료시간은 발급시간보다 빠를수 없습니다." }
        require(exp.isAfter(LocalDateTime.now())) { "만료된 토큰입니다." }
    }
}