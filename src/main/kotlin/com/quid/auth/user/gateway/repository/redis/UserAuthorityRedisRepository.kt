package com.quid.auth.user.gateway.repository.redis

import com.quid.auth.global.redis.RedisRepository
import com.quid.auth.user.domain.UserAuthority
import org.springframework.stereotype.Repository

@Repository
class UserAuthorityRedisRepository(
    private val redisListRepository: RedisRepository<UserAuthority>
) {
    operator fun get(userSeq: Long): List<UserAuthority>? =
        redisListRepository.getAll(makeKey(userSeq))

    operator fun set(userSeq: Long, value: List<UserAuthority>) {
        value.map { redisListRepository.add(makeKey(userSeq), it, 15) }
    }

    private fun makeKey(userSeq: Long): String =
        "userAuthority:$userSeq"
}