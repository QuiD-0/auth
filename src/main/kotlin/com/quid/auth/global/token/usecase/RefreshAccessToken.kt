package com.quid.auth.global.token.usecase

import com.quid.auth.global.token.domain.AccessToken
import com.quid.auth.global.token.domain.RefreshToken
import com.quid.auth.global.token.domain.Token
import com.quid.auth.global.token.gateway.repository.RefreshTokenRepository
import com.quid.auth.global.token.gateway.repository.model.UserToken
import com.quid.auth.global.token.gateway.web.response.TokenResponse
import org.springframework.stereotype.Service

fun interface RefreshAccessToken {
    operator fun invoke(accessTokenString: String, refreshTokenString: String): TokenResponse

    @Service
    class RefreshAccessTokenImpl(
        private val tokenEncoder: TokenEncoder,
        private val tokenDecoder: TokenDecoder,
        private val refreshTokenRepository: RefreshTokenRepository
    ) : RefreshAccessToken {
        override fun invoke(accessTokenString: String, refreshTokenString: String): TokenResponse {
            val accessToken: Token = tokenDecoder(accessTokenString)
            val refreshToken: Token = tokenDecoder(refreshTokenString)
            require(accessToken.isExpired()) { "access token is not expired" }
            require(refreshToken.isNotExpired()) { "refresh token is expired" }

            val refreshJti = refreshTokenRepository.findByUsername(accessToken.username)
            require(refreshToken.payload.jti == refreshJti) { "refresh token is not matched" }

            val newAccessToken = getNewAccessToken(accessToken.username)
            val newRefreshToken = getNewRefreshToken(accessToken.username)

            return TokenResponse(newAccessToken, newRefreshToken)
        }

        private fun getNewRefreshToken(username: String) =
            RefreshToken()
                .also {
                    refreshTokenRepository.save(
                        UserToken(
                            username,
                            it.payload.jti
                        )
                    )
                }.let { tokenEncoder(it) }

        private fun getNewAccessToken(username: String) =
            AccessToken(username)
                .run { tokenEncoder(this) }
    }
}