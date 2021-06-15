package ru.touchin.auth.core.scope.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.common.spring.jpa.models.BaseEntity
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "scopes", schema = SCHEMA)
class ScopeEntity : BaseEntity() {

    @Id
    lateinit var name: String

}
