package com.quid.auth.user.gateway.repository.jpa

import com.quid.auth.user.domain.UserAuthority
import java.time.LocalDateTime
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType.IDENTITY
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "user_authority")
class UserAuthorityEntity(
    @Id
    @GeneratedValue(strategy = IDENTITY)
    val authoritySeq: Long? = null,
    val authorityName: String,
    val regDate: LocalDateTime,
    val deleted: Boolean,
) {
    fun toUserAuthority() = UserAuthority(
        authoritySeq,
        authorityName,
        regDate,
        deleted,
    )
}