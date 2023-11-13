package com.quid.auth.authentication.blacklist.gateway.repository.jpa

import com.quid.auth.authentication.blacklist.domain.Blacklist
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Table(
    name = "blacklist", indexes = [
        Index(name = "idx_blacklistentity_userseq", columnList = "userSeq")
    ]
)
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