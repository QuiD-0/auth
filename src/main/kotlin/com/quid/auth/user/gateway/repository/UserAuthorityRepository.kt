package com.quid.auth.user.gateway.repository

import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.gateway.repository.jpa.UserAuthorityJpaRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository

interface UserAuthorityRepository {
    fun findById(authorityId: Long): UserAuthority

    @Repository
    class UserAuthorityRepositoryImpl(
        val jpaRepository: UserAuthorityJpaRepository
    ) : UserAuthorityRepository {
        override fun findById(authorityId: Long): UserAuthority =
            jpaRepository.findByIdOrNull(authorityId)
                ?.toUserAuthority()
                ?: throw IllegalArgumentException("UserAuthority not found: $authorityId")
    }
}