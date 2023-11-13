package com.quid.auth.authentication.user.usecase

import com.quid.auth.authentication.user.domain.RoleType.ROLE_ADMIN
import com.quid.auth.authentication.user.domain.User
import com.quid.auth.authentication.user.domain.UserAuthority
import com.quid.auth.authentication.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.authentication.user.gateway.repository.UserRepository
import org.springframework.stereotype.Service

interface FindAdmin {
    fun adminList(): List<User>
    fun authorityList(username: String): List<UserAuthority>

    @Service
    class FindAdminUseCase(
        val user: UserRepository,
        val authority: UserAuthorityRepository
    ): FindAdmin {
        override fun adminList(): List<User> =
            authority.findByName(ROLE_ADMIN.name)
                .map { it.userSeq }
                .let { user.findByUserSeqList(it) }

        override fun authorityList(username: String): List<UserAuthority> =
            user.findByUsername(username).userSeq
                .let { authority.findByUserSeq(it!!) }
    }
}