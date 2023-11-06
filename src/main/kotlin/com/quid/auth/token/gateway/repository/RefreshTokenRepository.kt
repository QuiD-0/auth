package com.quid.auth.token.gateway.repository

import com.quid.auth.token.gateway.repository.model.UserToken
import org.springframework.stereotype.Repository

interface RefreshTokenRepository {

    fun save(userToken: UserToken)
    fun findByUsername(username: String): String
    fun deleteByUsername(username: String)

    @Repository
    class InMemoryRefreshTokenRepository : RefreshTokenRepository {
        private val refreshToken: MutableMap<String, String> = mutableMapOf()

        override fun save(userToken: UserToken) {
            this.refreshToken[userToken.username] = userToken.refreshToken
        }

        override fun findByUsername(username: String): String =
            refreshToken[username]?: throw IllegalArgumentException("refresh token not found")

        override fun deleteByUsername(username: String) {
            refreshToken.remove(username)
        }
    }
}