package com.quid.auth.user.gateway.repository

import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.gateway.repository.jpa.UserAuthorityJpaRepository
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface UserAuthorityRepository {
    fun findByName(name: String): List<UserAuthority>
    fun findByUserSeq(userSeq: Long): List<UserAuthority>

    @Repository
    class UserAuthorityRepositoryImpl(
        val jpaRepository: UserAuthorityJpaRepository
    ) : UserAuthorityRepository {

        @Transactional(readOnly = true)
        override fun findByName(name: String): List<UserAuthority> =
            jpaRepository.findByAuthorityName(name)
                .map { it.toUserAuthority() }

        @Transactional(readOnly = true)
        override fun findByUserSeq(userSeq: Long): List<UserAuthority> =
            jpaRepository.findByUserSeq(userSeq).map { it.toUserAuthority() }
    }
}