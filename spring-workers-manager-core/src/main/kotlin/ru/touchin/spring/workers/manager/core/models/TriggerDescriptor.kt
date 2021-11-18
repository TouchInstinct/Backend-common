package ru.touchin.spring.workers.manager.core.models

import org.hibernate.annotations.GenericGenerator
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import ru.touchin.spring.workers.manager.core.models.base.BaseEntity
import ru.touchin.spring.workers.manager.core.models.enums.TriggerType
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "triggers", schema = SCHEMA)
class TriggerDescriptor : BaseEntity() {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    var id: String? = null

    /**
     * This field is **immutable**
     */
    @Column(name = "trigger_name", nullable = false)
    lateinit var triggerName: String

    /**
     * This field is **immutable**
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    lateinit var type: TriggerType

    /**
     * This field is **immutable**
     */
    @ManyToOne
    @JoinColumn(name = "worker_name", nullable = false)
    lateinit var worker: Worker

    /**
     * This field is **immutable**
     */
    @Column(name = "expression", nullable = false)
    lateinit var expression: String

    @Column(name = "disabled_at", nullable = true)
    var disabledAt: ZonedDateTime? = null

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: ZonedDateTime? = null

    fun isDisabled() = disabledAt != null

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is TriggerDescriptor) return false

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id?.hashCode() ?: 0
    }

}
