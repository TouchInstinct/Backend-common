package ru.touchin.spring.workers.manager.core.worker.models

import ru.touchin.common.spring.jpa.models.BaseEntity
import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import ru.touchin.spring.workers.manager.core.trigger.models.TriggerDescriptorEntity
import ru.touchin.spring.workers.manager.core.worker.enums.WorkerStatus
import java.time.ZonedDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.EnumType
import javax.persistence.Enumerated
import javax.persistence.FetchType
import javax.persistence.Id
import javax.persistence.OneToMany
import javax.persistence.Table

@Entity
@Table(name = "workers", schema = SCHEMA)
class WorkerEntity : BaseEntity() {

    @Id
    @Column(name = "worker_name", unique = true)
    lateinit var workerName: String

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    lateinit var status: WorkerStatus

    @Column(name = "disabled_at", nullable = true)
    var disabledAt: ZonedDateTime? = null

    @Column(name = "stopped_at", nullable = true)
    var stoppedAt: ZonedDateTime? = null

    @Column(name = "parallel_execution_enabled", nullable = false)
    var parallelExecutionEnabled: Boolean = false

    @OneToMany(mappedBy = "worker", orphanRemoval = true, fetch = FetchType.LAZY)
    lateinit var triggerDescriptors: Set<TriggerDescriptorEntity>

}
