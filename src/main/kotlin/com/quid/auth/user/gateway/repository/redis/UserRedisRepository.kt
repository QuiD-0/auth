package com.quid.auth.user.gateway.repository.redis

import com.quid.auth.global.redis.RedisRepository
import com.quid.auth.user.domain.User
import org.springframework.stereotype.Repository

@Repository
class UserRedisRepository(
    private val redisRepository: RedisRepository<User>
){
    operator fun get(username: String): User? =
        redisRepository[username]

    operator fun set(username: String, value: User) {
        redisRepository[username, value] = 15
    }
}