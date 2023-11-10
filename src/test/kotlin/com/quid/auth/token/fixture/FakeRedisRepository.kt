package com.quid.auth.token.fixture

import com.quid.auth.token.gateway.repository.RefreshTokenRepository
import com.quid.auth.token.gateway.repository.model.UserTokenJti

class FakeRedisRepository : RefreshTokenRepository {
    private val map = mutableMapOf<String, String>()

    override fun save(userToken: UserTokenJti) {
        map[userToken.username] = userToken.refreshTokenJti
    }

    override fun findByUsername(username: String): String {
        return map[username] ?: throw IllegalArgumentException("RefreshToken not found: $username")
    }

    override fun deleteByUsername(username: String) {
        map.remove(username)
    }
}