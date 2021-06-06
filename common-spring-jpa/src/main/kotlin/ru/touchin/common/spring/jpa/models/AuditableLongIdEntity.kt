@file:Suppress("unused")
package ru.touchin.common.spring.jpa.models

import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@Suppress("unused")
@MappedSuperclass
abstract class AuditableLongIdEntity : AuditableEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    open var id: Long? = null

}
