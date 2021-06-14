package ru.touchin.auth.core.device.models

import ru.touchin.auth.core.device.dto.enums.DevicePlatform
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.common.spring.jpa.models.AuditableUuidIdEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "devices")
class DeviceEntity: AuditableUuidIdEntity() {

    lateinit var platform: DevicePlatform

    @ManyToMany
    @JoinTable(
        name = "devices_users",
        joinColumns = [JoinColumn(name = "device_id")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    lateinit var users: Set<UserEntity>

    companion object {
        fun create(platform: DevicePlatform): DeviceEntity {
            return DeviceEntity().apply {
                this.platform = platform
            }
        }
    }

}
