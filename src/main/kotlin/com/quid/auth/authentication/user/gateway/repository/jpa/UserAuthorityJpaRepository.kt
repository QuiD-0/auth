package com.quid.auth.authentication.user.gateway.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthorityJpaRepository: JpaRepository<UserAuthorityEntity, Long> {
    fun findByAuthorityName(name: String): List<UserAuthorityEntity>
    fun findByUserSeq(userSeq: Long): List<UserAuthorityEntity>
}