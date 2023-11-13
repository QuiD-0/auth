package com.quid.auth.authentication.blacklist.gateway.repository.jpa

import org.springframework.data.jpa.repository.JpaRepository

interface BlacklistJpaRepository : JpaRepository<BlacklistEntity, Long> {
    fun findByUserSeq(userSeq: Long): List<BlacklistEntity>
}