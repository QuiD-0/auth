package com.quid.auth.authentication.user.usecase

import com.quid.auth.authentication.user.domain.UserAuthority
import com.quid.auth.authentication.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.authentication.user.gateway.repository.UserRepository
import org.springframework.stereotype.Service

interface GrantAuthority {

    operator fun invoke(userSeq: Long, authority: com.quid.auth.authentication.user.domain.AuthType)

    @Service
    class GrantAuthorityUseCase(
        private val userRepository: UserRepository,
        private val userAuthorityRepository: UserAuthorityRepository
    ) : GrantAuthority {
        override fun invoke(
            userSeq: Long,
            authority: com.quid.auth.authentication.user.domain.AuthType
        ) {
            takeIf { userRepository.existsById(userSeq) }
                ?.let { userAuthorityRepository.save(UserAuthority(userSeq, authority)) }
                ?: throw IllegalArgumentException("User not found: $userSeq")
        }
    }
}