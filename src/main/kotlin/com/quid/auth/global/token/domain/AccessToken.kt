package com.quid.auth.global.token.domain

import io.jsonwebtoken.Header.COMPRESSION_ALGORITHM
import io.jsonwebtoken.Header.JWT_TYPE
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.*
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets.*
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

data class AccessToken(
    val jti: String = UUID.randomUUID().toString(),
    val iss: String = "QUID_LAB",
    val sub: String,
    val exp: LocalDateTime,
    val iat: LocalDateTime = LocalDateTime.now(),
    val secret: String,
) {
    init {
        require(exp.isAfter(iat)) { "토큰 만료시간은 발급시간보다 빠를수 없습니다." }
        require(exp.isAfter(LocalDateTime.now())) { "만료된 토큰입니다." }
    }

    fun toJWT(): String = Jwts.builder()
        .apply {
            setHeader(mapOf("typ" to JWT_TYPE, "alg" to COMPRESSION_ALGORITHM))
            setSubject(sub)
            setIssuer(iss)
            setExpiration(Date(exp.toEpochSecond(ZoneOffset.UTC)))
            setIssuedAt(Date(iat.toEpochSecond(ZoneOffset.UTC)))
            setId(jti)
        }.signWith(Keys.hmacShaKeyFor(secret.toByteArray(UTF_8)), HS256)
        .compact()
}