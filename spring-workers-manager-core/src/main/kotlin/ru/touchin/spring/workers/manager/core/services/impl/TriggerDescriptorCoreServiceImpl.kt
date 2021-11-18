package ru.touchin.spring.workers.manager.core.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.repositories.TriggerDescriptorRepository
import ru.touchin.spring.workers.manager.core.repositories.upsert
import ru.touchin.spring.workers.manager.core.services.TriggerDescriptorCoreService
import java.time.ZonedDateTime

@Service
class TriggerDescriptorCoreServiceImpl(
    private val triggerDescriptorRepository: TriggerDescriptorRepository
) : TriggerDescriptorCoreService {

    @Transactional
    override fun save(triggerDescriptor: TriggerDescriptor) {
        triggerDescriptorRepository.save(triggerDescriptor)
    }

    @Transactional
    override fun delete(triggerDescriptor: TriggerDescriptor) {
        triggerDescriptorRepository.upsert(triggerDescriptor) {
            it.deletedAt = ZonedDateTime.now()
        }
    }

    override fun getByWorkerName(workerName: String): List<TriggerDescriptor> {
        return triggerDescriptorRepository.findAll(workerName)
    }

}
