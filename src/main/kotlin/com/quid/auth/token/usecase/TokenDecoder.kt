package com.quid.auth.token.usecase

import com.quid.auth.token.domain.AccessToken
import com.quid.auth.token.domain.Payload
import com.quid.auth.token.domain.Token
import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
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
            try {
                Jwts.parserBuilder().setSigningKey(secret.toByteArray(StandardCharsets.UTF_8)).build()
                    .parse(token)
                    .let { it.body as Claims }
                    .let { Payload(it) }
                    .let { AccessToken(it) }
            } catch (exp: ExpiredJwtException){
                Payload(exp.claims)
                    .run { AccessToken(this) }
            }

    }
}