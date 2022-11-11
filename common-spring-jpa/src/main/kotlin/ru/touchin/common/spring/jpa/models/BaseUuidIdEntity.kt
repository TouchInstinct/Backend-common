@file:Suppress("unused")
package ru.touchin.common.spring.jpa.models

import org.hibernate.annotations.GenericGenerator
import java.util.UUID
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@Suppress("unused")
@MappedSuperclass
abstract class BaseUuidIdEntity : BaseEntity() {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    open var id: UUID? = null
        @Suppress("RedundantSetter")
        protected set(id) {
            field = id
        }

}
