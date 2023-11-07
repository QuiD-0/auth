package com.quid.auth.user.usecase

import com.quid.auth.user.domain.UserAuthority
import com.quid.auth.user.gateway.repository.UserAuthorityRepository
import org.springframework.stereotype.Service

interface GrantAuthority {

    operator fun invoke(userAuthority: UserAuthority)

    @Service
    class GrantAuthorityUseCase(
        private val userAuthorityRepository: UserAuthorityRepository
    ) : GrantAuthority {
        override fun invoke(userAuthority: UserAuthority) {
            userAuthorityRepository.save(userAuthority)
        }
    }
}