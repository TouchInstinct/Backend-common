package ru.touchin.settings.models

import ru.touchin.common.spring.jpa.models.AuditableEntity
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class AbstractSettingEntity: AuditableEntity() {

    @Id
    lateinit var key: String

    lateinit var value: String

}
