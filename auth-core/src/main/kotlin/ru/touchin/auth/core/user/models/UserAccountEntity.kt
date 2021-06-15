package ru.touchin.auth.core.user.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.auth.core.user.dto.enums.IdentifierType
import ru.touchin.common.spring.jpa.models.AuditableUuidIdEntity
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "user_accounts", schema = SCHEMA)
class UserAccountEntity: AuditableUuidIdEntity() {

    lateinit var username: String

    var password: String? = null

    @Enumerated(EnumType.STRING)
    lateinit var identifierType: IdentifierType

    @ManyToOne
    @JoinColumn(name = "user_id")
    lateinit var user: UserEntity

}
