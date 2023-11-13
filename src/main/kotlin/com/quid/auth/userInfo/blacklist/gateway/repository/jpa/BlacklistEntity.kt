package com.quid.auth.userInfo.blacklist.gateway.repository.jpa

import com.quid.auth.userInfo.blacklist.domain.Blacklist
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType.IDENTITY
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Table(name = "blacklist")
@Where(clause = "deleted = false")
class BlacklistEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val blacklistSeq: Long?,
    val userSeq: Long,
    val description: String,
    val expireDate: LocalDateTime,
    val regDate: LocalDateTime,
    val deleted: Boolean,
) {
    constructor(blacklist: Blacklist) : this(
        blacklist.blacklistSeq,
        blacklist.userSeq,
        blacklist.description,
        blacklist.expireDate,
        blacklist.regDate,
        blacklist.deleted,
    )

    fun toDomain() = Blacklist(
        blacklistSeq,
        userSeq,
        description,
        expireDate,
        regDate,
        deleted,
    )

}