package com.quid.auth.user.gateway.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface UserJpaRepository: JpaRepository<UserEntity, Long> {
    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
    fun findByAuthorityId(authoritySeq: Long): List<UserEntity>
}