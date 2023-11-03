package com.quid.auth.user.usecase

import com.quid.auth.global.token.gateway.repository.RefreshTokenRepository
import com.quid.auth.global.token.domain.AccessToken
import com.quid.auth.global.token.domain.RefreshToken
import com.quid.auth.global.token.gateway.repository.model.UserToken
import com.quid.auth.global.token.usecase.TokenEncoder
import com.quid.auth.user.domain.UserDetail
import com.quid.auth.user.gateway.web.response.TokenResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.stereotype.Service
import java.time.LocalDateTime

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
                .let {
                    AccessToken(
                        it.username,
                        LocalDateTime.now().plusMinutes(30)
                    )
                }
                .let { tokenEncoder(it) }

            val refreshToken = RefreshToken(LocalDateTime.now().plusDays(30))
                .also { refreshTokenRepository.save(UserToken(username, it.payload.jti)) }
                .run { tokenEncoder(this) }

            return TokenResponse(accessToken, refreshToken)
        }

    }
}