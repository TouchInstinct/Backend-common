@file:Suppress("unused")
package ru.touchin.common.spring.jpa.models

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class AuditableEntity : Serializable {

    @CreatedDate
    lateinit var createdAt: ZonedDateTime

    @CreatedBy
    lateinit var createdBy: String

    @LastModifiedDate
    var updatedAt: ZonedDateTime? = null

    @LastModifiedBy
    var updatedBy: String? = null

}
