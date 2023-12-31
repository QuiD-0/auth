package com.quid.auth.authentication.user.usecase

import com.quid.auth.authentication.user.domain.UserDetail
import com.quid.auth.token.domain.AccessToken
import com.quid.auth.token.domain.Payload
import com.quid.auth.token.domain.RefreshToken
import com.quid.auth.token.gateway.repository.RefreshTokenRepository
import com.quid.auth.token.gateway.repository.model.UserTokenJti
import com.quid.auth.token.gateway.web.response.TokenResponse
import com.quid.auth.token.usecase.TokenEncoder
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service

fun interface LogIn {
    operator fun invoke(username: String, password: String): TokenResponse

    @Service
    class LoginUseCase(
        private val authenticationManagerBuilder: AuthenticationManagerBuilder,
        private val tokenEncoder: TokenEncoder,
        private val refreshTokenRepository: RefreshTokenRepository,
    ) : LogIn {

        override fun invoke(username: String, password: String): TokenResponse {
            val accessToken = UsernamePasswordAuthenticationToken(username, password)
                .let { authenticationManagerBuilder.getObject().authenticate(it) }
                .let { it.principal as UserDetail }
                .let { Payload.accessType(it.username) }
                .let { AccessToken(it) }
                .let { tokenEncoder(it) }

            val refreshToken = RefreshToken()
                .also { refreshTokenRepository.save(UserTokenJti(username, it.id)) }
                .run { tokenEncoder(this) }

            return TokenResponse(accessToken, refreshToken)
        }

    }
}