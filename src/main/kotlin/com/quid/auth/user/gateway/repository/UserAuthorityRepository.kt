package com.quid.auth.user.gateway.repository

import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.gateway.repository.jpa.UserAuthorityJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional

interface UserAuthorityRepository {
    fun findById(authorityId: Long): UserAuthority

    @Repository
    class UserAuthorityRepositoryImpl(
        val jpaRepository: UserAuthorityJpaRepository
    ) : UserAuthorityRepository {

        @Transactional(readOnly = true)
        override fun findById(authorityId: Long): UserAuthority =
            jpaRepository.findByIdOrNull(authorityId)
                ?.toUserAuthority()
                ?: throw IllegalArgumentException("UserAuthority not found: $authorityId")
    }
}