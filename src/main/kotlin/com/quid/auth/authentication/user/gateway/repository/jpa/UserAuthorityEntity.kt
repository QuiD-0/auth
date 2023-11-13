package com.quid.auth.authentication.user.gateway.repository.jpa

import com.quid.auth.authentication.user.domain.UserAuthority
import jakarta.persistence.*
import jakarta.persistence.GenerationType.IDENTITY
import org.hibernate.annotations.Where
import java.time.LocalDateTime

@Entity
@Table(name = "user_authority", indexes = [
    Index(name = "idx_user_authority_seq", columnList = "userSeq, authorityName", unique = true),
])
@Where(clause = "deleted = false")
class UserAuthorityEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val authoritySeq: Long? = null,
    val userSeq: Long,
    val authorityName: String,
    val regDate: LocalDateTime,
    val deleted: Boolean,
) {
    constructor(userAuthority: UserAuthority) : this(
        userAuthority.authoritySeq,
        userAuthority.userSeq,
        userAuthority.authorityName.name,
        userAuthority.regDate,
        userAuthority.deleted,
    )

    fun toUserAuthority() = UserAuthority(
        authoritySeq,
        userSeq,
        com.quid.auth.authentication.user.domain.AuthType.of(authorityName),
        regDate,
        deleted,
    )
}