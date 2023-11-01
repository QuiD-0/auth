package com.quid.auth.global.token.domain

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm.HS256
import io.jsonwebtoken.security.Keys
import java.nio.charset.StandardCharsets.UTF_8
import java.time.ZoneOffset
import java.util.*

data class AccessToken(
    val header: TokenHeader = TokenHeader(),
    val payload: TokenPayload,
    val signature: TokenSignature,
) {

    fun encode(): String = Jwts.builder().apply {
            setHeader(header.value)
            setSubject(payload.sub)
            setIssuer(payload.iss)
            setExpiration(Date(payload.exp.toEpochSecond(ZoneOffset.UTC) * 1000))
            setIssuedAt(Date(payload.iat.toEpochSecond(ZoneOffset.UTC) * 1000))
            setId(payload.jti)
        }.signWith(Keys.hmacShaKeyFor(signature.secret.toByteArray(UTF_8)), HS256).compact()
}

fun decodeToken(value: String, secret: String): AccessToken =
    Jwts.parserBuilder().setSigningKey(secret.toByteArray(UTF_8)).build().parse(value)
        .let { it.body as Claims }
        .let { makePayload(it) }
        .let { AccessToken(payload = it, signature = TokenSignature(secret)) }
