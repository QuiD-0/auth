package com.quid.auth.user.usecase

import com.quid.auth.user.domain.RoleType.ROLE_ADMIN
import com.quid.auth.user.domain.User
import com.quid.auth.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.user.gateway.repository.UserRepository
import org.springframework.stereotype.Service

interface FindAdmin {
    fun adminList(): List<User>

    @Service
    class FindAdminUseCase(
        val user: UserRepository,
        val authority: UserAuthorityRepository
    ): FindAdmin {
        override fun adminList(): List<User> =
            authority.findByName(ROLE_ADMIN.name)
                .map { it.userSeq }
                .let { user.findByUserSeqList(it) }
    }
}