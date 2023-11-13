package com.quid.auth.blacklist.gateway.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface BlacklistJpaRepository : JpaRepository<BlacklistEntity, Long> {
    fun existsByUserSeq(userSeq: Long): Boolean
    fun findByUserSeq(userSeq: Long): List<BlacklistEntity>
}