@file:Suppress("unused")
package ru.touchin.common.spring.jpa.models

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.io.Serializable
import java.time.ZonedDateTime
import javax.persistence.EntityListeners
import javax.persistence.MappedSuperclass

@EntityListeners(AuditingEntityListener::class)
@MappedSuperclass
abstract class BaseEntity : Serializable {

    @CreatedDate(updatable = false)
    lateinit var createdAt: ZonedDateTime

    @LastModifiedDate
    var updatedAt: ZonedDateTime? = null

}
