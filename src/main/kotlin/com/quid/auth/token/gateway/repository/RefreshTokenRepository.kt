package com.quid.auth.token.gateway.repository

import com.quid.auth.token.gateway.repository.model.UserTokenJti
import org.springframework.stereotype.Repository

interface RefreshTokenRepository {

    fun save(userTokenJti: UserTokenJti)
    fun findByUsername(username: String): String
    fun deleteByUsername(username: String)

    @Repository
    class InMemoryRefreshTokenRepository : RefreshTokenRepository {
        private val refreshToken: MutableMap<String, String> = mutableMapOf()

        override fun save(userTokenJti: UserTokenJti) {
            this.refreshToken[userTokenJti.username] = userTokenJti.refreshTokenJti
        }

        override fun findByUsername(username: String): String =
            refreshToken[username]?: throw IllegalArgumentException("refresh token not found")

        override fun deleteByUsername(username: String) {
            refreshToken.remove(username)
        }
    }
}