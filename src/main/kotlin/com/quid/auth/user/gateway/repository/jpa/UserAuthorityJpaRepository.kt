package com.quid.auth.user.gateway.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserAuthorityJpaRepository: JpaRepository<UserAuthorityEntity, Long> {
    fun findByAuthorityName(name: String): UserAuthorityEntity?
}