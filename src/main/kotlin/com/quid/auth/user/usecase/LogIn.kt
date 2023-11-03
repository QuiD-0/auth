package com.quid.auth.user.usecase

import com.quid.auth.global.token.domain.AccessToken
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
    ) : LogIn {

        override fun invoke(username: String, password: String): TokenResponse =
            UsernamePasswordAuthenticationToken(username, password)
                .let { authenticationManagerBuilder.getObject().authenticate(it) }
                .let { it.principal as UserDetail }
                .let {
                    AccessToken(
                        username = it.username,
                        exp = LocalDateTime.now().plusHours(1)
                    )
                }
                .let { tokenEncoder(it) }
                .let { TokenResponse(it) }
    }
}