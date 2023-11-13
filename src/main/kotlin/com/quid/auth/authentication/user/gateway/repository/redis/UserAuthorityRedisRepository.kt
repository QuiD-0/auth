package com.quid.auth.authentication.user.gateway.repository.redis

import com.quid.auth.authentication.user.domain.UserAuthority
import com.quid.auth.global.redis.RedisBaseRepository
import org.springframework.stereotype.Repository

@Repository
class UserAuthorityRedisRepository(
    private val redisListRepository: RedisBaseRepository<UserAuthorityRedisHash>
) {
    operator fun get(userSeq: Long): List<UserAuthority>? =
        redisListRepository.getAll(makeKey(userSeq))
            ?.map { it.toDomain() }
            ?.let { it.ifEmpty { null } }

    operator fun set(userSeq: Long, value: List<UserAuthority>) {
        value.forEach { redisListRepository.add(makeKey(userSeq), UserAuthorityRedisHash(it), 15) }
            .also { redisListRepository.expire(makeKey(userSeq), 15) }
    }

    private fun makeKey(userSeq: Long): String =
        "userAuthority:$userSeq"
}