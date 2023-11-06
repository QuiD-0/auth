package com.quid.auth.token.usecase

import com.quid.auth.token.domain.Token
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.time.ZoneOffset
import java.util.*

fun interface TokenEncoder {

    operator fun invoke(token: Token): String

    @Service
    class JwtTokenEncoder(
        @Value("\${jwt.secret}")
        private val secret: String
    ) : TokenEncoder {

        override fun invoke(token: Token): String = Jwts.builder().apply {
            setHeader(token.header.value)
            setSubject(token.payload.sub.name)
            setIssuer(token.payload.iss)
            setExpiration(Date(token.payload.exp.toEpochSecond(ZoneOffset.UTC) * 1000))
            setIssuedAt(Date(token.payload.iat.toEpochSecond(ZoneOffset.UTC) * 1000))
            setId(token.payload.jti)
            claim("username", token.username)
        }.signWith(
            Keys.hmacShaKeyFor(secret.toByteArray(StandardCharsets.UTF_8)),
            SignatureAlgorithm.HS256
        ).compact()

    }
}