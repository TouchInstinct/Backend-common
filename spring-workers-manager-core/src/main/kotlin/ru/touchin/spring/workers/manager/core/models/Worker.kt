package ru.touchin.spring.workers.manager.core.models

import ru.touchin.spring.workers.manager.WorkersManagerConfiguration.Companion.SCHEMA
import ru.touchin.spring.workers.manager.core.models.base.BaseEntity
import ru.touchin.spring.workers.manager.core.models.enums.WorkerStatus
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
class Worker : BaseEntity() {

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
    lateinit var triggerDescriptors: Set<TriggerDescriptor>

    fun isStopped() = stoppedAt != null

    fun isDisabled() = disabledAt != null

    fun getEnabledTriggerDescriptors() = triggerDescriptors.filter { !it.isDisabled() && it.deletedAt == null}

    fun getAllTriggerDescriptors() = triggerDescriptors.filter { it.deletedAt == null}

}
