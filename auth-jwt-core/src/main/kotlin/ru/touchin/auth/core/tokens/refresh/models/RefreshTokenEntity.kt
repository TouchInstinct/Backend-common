@file:Suppress("unused")

package ru.touchin.auth.core.tokens.refresh.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.scope.models.ScopeEntity
import ru.touchin.auth.core.tokens.refresh.exceptions.RefreshTokenExpiredException
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.common.date.DateUtils.isExpired
import ru.touchin.common.spring.jpa.models.AuditableUuidIdEntity
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "refresh_tokens", schema = SCHEMA)
class RefreshTokenEntity : AuditableUuidIdEntity() {

    lateinit var value: String

    lateinit var expiresAt: ZonedDateTime

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity

    @ManyToOne
    @JoinColumn(name = "device_id")
    var device: DeviceEntity? = null

    @ManyToMany
    @JoinTable(
        name = "refresh_token_scopes",
        joinColumns = [JoinColumn(name = "refresh_token_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_name")]
    )
    lateinit var scopes: Set<ScopeEntity>

    fun validate(): RefreshTokenEntity = this.apply {
        if (expiresAt.isExpired()) {
            throw RefreshTokenExpiredException(value)
        }
    }

}
