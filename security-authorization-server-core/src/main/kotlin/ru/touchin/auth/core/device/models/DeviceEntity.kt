package ru.touchin.auth.core.device.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.common.devices.enums.DevicePlatform
import ru.touchin.common.spring.jpa.models.AuditableUuidIdEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "devices", schema = SCHEMA)
class DeviceEntity: AuditableUuidIdEntity() {

    lateinit var platform: DevicePlatform

    @ManyToMany
    @JoinTable(
        name = "devices_users",
        schema = SCHEMA,
        joinColumns = [JoinColumn(name = "device_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    lateinit var users: Set<UserEntity>

    override fun equals(other: Any?): Boolean {
        if (other == null || this.javaClass != other.javaClass) {
            return false
        }

        other as DeviceEntity

        return this.id == other.id
    }

    override fun hashCode(): Int {
        return this.id.hashCode()
    }

    companion object {
        fun create(platform: DevicePlatform): DeviceEntity {
            return DeviceEntity().apply {
                this.platform = platform
            }
        }
    }

}
