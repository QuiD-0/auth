package com.quid.auth.user.gateway.repository.jpa

import com.quid.auth.user.domain.AuthType
import com.quid.auth.user.domain.UserAuthority
import org.hibernate.annotations.Where
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_authority")
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
        AuthType.of(authorityName),
        regDate,
        deleted,
    )
}