package com.quid.auth.user.gateway.repository

import com.quid.auth.user.domain.User
import com.quid.auth.user.gateway.repository.jpa.UserEntity
import com.quid.auth.user.gateway.repository.jpa.UserJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface UserRepository {

    fun save(user: User): User
    fun findByUsername(username: String): User
    fun existsByUsername(username: String): Boolean
    fun findByUserSeqList(ids: List<Long>): List<User>

    @Repository
    class UserRepositoryImpl(
        val userJpaRepository: UserJpaRepository
    ) : UserRepository {

        @Transactional
        override fun save(user: User): User = userJpaRepository.save(UserEntity(user)).toUser()

        @Transactional(readOnly = true)
        override fun findByUsername(username: String): User =
            userJpaRepository.findByUsername(username)
                ?.toUser()
                ?: throw IllegalArgumentException("User not found: $username")

        @Transactional(readOnly = true)
        override fun existsByUsername(username: String): Boolean =
            userJpaRepository.existsByUsername(username)

        override fun findByUserSeqList(ids: List<Long>) =
            userJpaRepository.findAllById(ids)
                .map { it.toUser() }
    }

}