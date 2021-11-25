package ru.touchin.spring.workers.manager.core.execution.models

import ru.touchin.common.spring.jpa.models.BaseUuidIdEntity
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import ru.touchin.spring.workers.manager.core.execution.enums.ExecutionStatus
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.JoinColumn
import javax.persistence.ManyToOne
import javax.persistence.Table

@Entity
@Table(name = "executions", schema = SCHEMA)
class ExecutionEntity : BaseUuidIdEntity() {

    @Column(name = "worker_name", nullable = false)
    lateinit var workerName: String

    @ManyToOne
    @JoinColumn(name = "trigger_id", nullable = true)
    var triggerDescriptor: TriggerDescriptorEntity? = null

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    lateinit var status: ExecutionStatus

    @Column(name = "error_message", nullable = true)
    var errorMessage: String? = null

    @Column(name = "error_code", nullable = true)
    var errorCode: Int? = null

    @Column(name = "started_at", nullable = true)
    var startedAt: ZonedDateTime? = null

    @Column(name = "finished_at", nullable = true)
    var finishedAt: ZonedDateTime? = null

}
