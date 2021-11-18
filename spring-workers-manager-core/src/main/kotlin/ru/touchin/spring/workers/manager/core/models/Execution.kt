package ru.touchin.spring.workers.manager.core.models

import org.hibernate.annotations.GenericGenerator
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import ru.touchin.spring.workers.manager.core.models.base.BaseEntity
import ru.touchin.spring.workers.manager.core.models.enums.ExecutionStatus
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
@Table(name = "executions", schema = SCHEMA)
class Execution : BaseEntity() {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "id", unique = true)
    var id: String? = null

    @Column(name = "worker_name", nullable = false)
    lateinit var workerName: String

    @ManyToOne
    @JoinColumn(name = "trigger_id", nullable = true)
    var triggerDescriptor: TriggerDescriptor? = null

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
