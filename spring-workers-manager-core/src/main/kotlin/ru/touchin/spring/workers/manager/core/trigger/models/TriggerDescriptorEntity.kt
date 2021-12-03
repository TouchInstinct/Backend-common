package ru.touchin.spring.workers.manager.core.trigger.models

import ru.touchin.common.spring.jpa.models.BaseUuidIdEntity
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import ru.touchin.spring.workers.manager.core.trigger.enums.TriggerType
import ru.touchin.spring.workers.manager.core.worker.models.WorkerEntity
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "triggers", schema = SCHEMA)
class TriggerDescriptorEntity : BaseUuidIdEntity() {

    @Column(name = "trigger_name", nullable = false)
    lateinit var triggerName: String

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    lateinit var type: TriggerType

    @ManyToOne
    @JoinColumn(name = "worker_name", nullable = false)
    lateinit var worker: WorkerEntity

    @Column(name = "expression", nullable = false)
    lateinit var expression: String

    @Column(name = "disabled_at", nullable = true)
    var disabledAt: ZonedDateTime? = null

    @Column(name = "deleted_at", nullable = true)
    var deletedAt: ZonedDateTime? = null

}
