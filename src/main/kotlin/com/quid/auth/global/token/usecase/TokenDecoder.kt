package com.quid.auth.global.token.usecase

import com.quid.auth.global.token.domain.AccessToken
import com.quid.auth.global.token.domain.Payload
import com.quid.auth.global.token.domain.Token
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets

interface TokenDecoder {

    operator fun invoke(token: String): Token

    @Service
    class JwtTokenDecoder(
        @Value("\${jwt.secret}")
        private val secret: String
    ): TokenDecoder {

        override fun invoke(token: String): Token =
            Jwts.parserBuilder().setSigningKey(secret.toByteArray(StandardCharsets.UTF_8)).build()
                .parse(token)
                .let { it.body as Claims }
                .let { Payload(it) }
                .let { AccessToken(it) }
    }
}