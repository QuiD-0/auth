package com.quid.auth.token.usecase

import com.quid.auth.token.domain.AccessToken
import com.quid.auth.token.domain.Payload
import com.quid.auth.token.domain.RefreshToken
import com.quid.auth.token.domain.TokenType
import com.quid.auth.token.gateway.repository.RefreshTokenRepository
import com.quid.auth.token.gateway.repository.model.UserToken
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.time.LocalDateTime

class RefreshAccessTokenTest{

    private val tokenSecret = "this_is_test_secret_key_for_token_encode_and_decode"
    private val tokenEncoder: TokenEncoder = TokenEncoder.JwtTokenEncoder(tokenSecret)
    private val tokenDecoder: TokenDecoder = TokenDecoder.JwtTokenDecoder(tokenSecret)
    private val refreshTokenRepository: RefreshTokenRepository = RefreshTokenRepository.InMemoryRefreshTokenRepository()

    private val refreshAccessToken: RefreshAccessToken = RefreshAccessToken.RefreshAccessTokenImpl(
        tokenEncoder = tokenEncoder,
        tokenDecoder = tokenDecoder,
        refreshTokenRepository = refreshTokenRepository
    )

    @Test
    @DisplayName("access token이 만료되었을 경우 새로운 access token과 refresh token을 발급")
    fun tokenRefreshTest(){
        val expiredToken = AccessToken(
            payload = Payload(
                sub = TokenType.ACCESS,
                username = "test",
                exp = LocalDateTime.now().minusMinutes(10),
                iat = LocalDateTime.now().minusMinutes(20)
            )
        )
        val refreshToken = RefreshToken()
        refreshTokenRepository.save(UserToken("test", refreshToken.id))

        refreshAccessToken(tokenEncoder(expiredToken), tokenEncoder(refreshToken))
            .also {
                assertNotEquals(it.accessToken, expiredToken)
                assertNotEquals(it.refreshToken, refreshToken)
                assertEquals(refreshTokenRepository.findByUsername("test"), tokenDecoder(it.refreshToken).id)
            }
    }

    @Test
    @DisplayName("access token이 만료되지 않았을 경우 에러 발생")
    fun refreshTokenFail(){
        val expiredToken = AccessToken(
            payload = Payload(
                sub = TokenType.ACCESS,
                username = "test",
                exp = LocalDateTime.now().plusMinutes(10),
                iat = LocalDateTime.now().minusMinutes(20)
            )
        )
        val refreshToken = RefreshToken()
        refreshTokenRepository.save(UserToken("test", refreshToken.id))

        assertThrows<IllegalArgumentException> {
            refreshAccessToken(tokenEncoder(expiredToken), tokenEncoder(refreshToken))
        }
    }


}