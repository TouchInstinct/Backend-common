package ru.touchin.auth.core.user.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.scope.models.ScopeEntity
import ru.touchin.common.spring.jpa.models.AuditableEntity
import java.time.ZonedDateTime
import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "users", schema = SCHEMA)
class UserEntity: AuditableEntity() {

    @Id
    var id: UUID? = null

    var anonymous: Boolean = true

    var confirmedAt: ZonedDateTime? = null

    @ManyToMany
    @JoinTable(
        name = "devices_users",
        schema = SCHEMA,
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "device_id")]
    )
    lateinit var devices: Set<DeviceEntity>

    @ManyToMany
    @JoinTable(
        name = "users_scopes",
        schema = SCHEMA,
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "scope_name")]
    )
    lateinit var scopes: Set<ScopeEntity>

}
