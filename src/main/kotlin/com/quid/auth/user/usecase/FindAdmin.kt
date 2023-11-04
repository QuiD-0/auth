package com.quid.auth.user.usecase

import com.quid.auth.user.domain.AuthorityType.ADMIN
import com.quid.auth.user.domain.UserDetail
import com.quid.auth.user.gateway.repository.UserAuthorityRepository
import com.quid.auth.user.gateway.repository.UserRepository
import org.springframework.stereotype.Service

interface FindAdmin {
    fun adminList(): List<UserDetail>

    @Service
    class FindAdminUseCase(
        val user: UserRepository,
        val authority: UserAuthorityRepository
    ): FindAdmin {
        override fun adminList(): List<UserDetail> =
            authority.findByName(ADMIN.name)
                .let { Pair(user.findByAuthoritySeq(it.authoritySeq!!), it) }
                .let { (users, authority) -> users.map { UserDetail(it, authority) } }
    }
}