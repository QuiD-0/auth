package com.quid.auth.user.usecase

import com.quid.auth.user.domain.AuthType
import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.user.gateway.repository.UserRepository
import org.springframework.stereotype.Service

interface GrantAuthority {

    operator fun invoke(userSeq: Long, authority: AuthType)

    @Service
    class GrantAuthorityUseCase(
        private val userRepository: UserRepository,
        private val userAuthorityRepository: UserAuthorityRepository
    ) : GrantAuthority {
        override fun invoke(userSeq: Long, authority: AuthType) {
            takeIf { userRepository.existsById(userSeq) }
                ?.let { userAuthorityRepository.save(UserAuthority(userSeq, authority)) }
                ?: throw IllegalArgumentException("User not found: $userSeq")
        }
    }
}