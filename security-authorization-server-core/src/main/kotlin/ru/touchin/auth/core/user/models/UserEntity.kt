package ru.touchin.auth.core.user.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.auth.core.device.models.DeviceEntity
import ru.touchin.auth.core.scope.models.ScopeEntity
import ru.touchin.common.spring.jpa.models.AuditableUuidIdEntity
import java.time.ZonedDateTime
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "users", schema = SCHEMA)
class UserEntity : AuditableUuidIdEntity() {

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
    lateinit var scopes: MutableSet<ScopeEntity>

    fun addScopes(scopes: Collection<ScopeEntity>) {
        this.scopes.addAll(scopes)
        scopes.forEach { it.users.add(this) }
    }

}
