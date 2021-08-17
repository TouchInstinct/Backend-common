@file:Suppress("unused")

package ru.touchin.auth.core.scope.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.common.spring.jpa.models.BaseUuidIdEntity
import javax.persistence.Entity
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "scope_groups", schema = SCHEMA)
class ScopeGroupEntity : BaseUuidIdEntity() {

    lateinit var groupName: String

    @ManyToOne
    @JoinColumn(name = "scope_name")
    lateinit var scope: ScopeEntity

    companion object {
        const val DEFAULT_USER_SCOPE_GROUP = "DefaultUser"
    }

}
