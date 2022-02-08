package ru.touchin.auth.core.scope.models

import ru.touchin.auth.core.configurations.AuthCoreDatabaseConfiguration.Companion.SCHEMA
import ru.touchin.auth.core.user.models.UserEntity
import ru.touchin.common.spring.jpa.models.BaseEntity
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.JoinTable
import javax.persistence.ManyToMany
import javax.persistence.Table

@Entity
@Table(name = "scopes", schema = SCHEMA)
class ScopeEntity : BaseEntity() {

    @Id
    lateinit var name: String

    @ManyToMany
    @JoinTable(
        name = "users_scopes",
        schema = SCHEMA,
        joinColumns = [JoinColumn(name = "scope_name")],
        inverseJoinColumns = [JoinColumn(name = "user_id")]
    )
    lateinit var users: MutableSet<UserEntity>

    fun addUsers(users: Collection<UserEntity>) {
        this.users.addAll(users)
    }

}
