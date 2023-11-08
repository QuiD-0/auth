package com.quid.auth.user.gateway.repository

import com.quid.auth.user.domain.User
import com.quid.auth.user.gateway.repository.jpa.UserEntity
import com.quid.auth.user.gateway.repository.jpa.UserJpaRepository
import com.quid.auth.user.gateway.repository.redis.UserRedisRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface UserRepository {

    fun save(user: User): User
    fun findByUsername(username: String): User
    fun existsByUsername(username: String): Boolean
    fun findByUserSeqList(ids: List<Long>): List<User>
    fun existsById(userSeq: Long): Boolean

    @Repository
    class UserRepositoryImpl(
        private val repository: UserJpaRepository,
        private val cache: UserRedisRepository
    ) : UserRepository {

        @Transactional
        override fun save(user: User): User = repository.save(UserEntity(user)).toUser()

        @Transactional(readOnly = true)
        override fun findByUsername(username: String): User =
            cache[username]
                ?: repository.findByUsername(username)
                    ?.toUser()
                    ?.also { cache[username] = it }
                ?: throw IllegalArgumentException("User not found: $username")

        @Transactional(readOnly = true)
        override fun existsByUsername(username: String): Boolean =
            repository.existsByUsername(username)

        override fun findByUserSeqList(ids: List<Long>) =
            repository.findAllById(ids)
                .map { it.toUser() }

        override fun existsById(userSeq: Long) =
            repository.existsById(userSeq)
    }

}