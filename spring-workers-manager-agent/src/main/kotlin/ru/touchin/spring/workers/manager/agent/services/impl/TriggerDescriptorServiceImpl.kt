package ru.touchin.spring.workers.manager.agent.services.impl

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import ru.touchin.spring.workers.manager.agent.services.TriggerDescriptorService
import ru.touchin.spring.workers.manager.agent.triggers.InitialTriggerDescriptorsProvider
import ru.touchin.spring.workers.manager.core.models.TriggerDescriptor
import ru.touchin.spring.workers.manager.core.models.Worker
import ru.touchin.spring.workers.manager.core.repositories.TriggerDescriptorRepository
import ru.touchin.spring.workers.manager.core.repositories.upsert
import java.time.ZonedDateTime

@Service
class TriggerDescriptorServiceImpl(
    private val triggerDescriptorRepository: TriggerDescriptorRepository,
    private val initialTriggerDescriptorProviders: List<InitialTriggerDescriptorsProvider>
) : TriggerDescriptorService {

    @Transactional
    override fun createDefaultTriggerDescriptors(worker: Worker): List<TriggerDescriptor> {
        return triggerDescriptorRepository.saveAll(
            initialTriggerDescriptorProviders
                .filter { it.applicableFor(worker) }
                .flatMap { it.createInitialTriggerDescriptors(worker) }
        )
    }

    @Transactional
    override fun setTriggerDescriptorDisable(
        triggerDescriptor: TriggerDescriptor,
        disabledAt: ZonedDateTime?
    ) {
        triggerDescriptorRepository.upsert(triggerDescriptor) {
            it.disabledAt = disabledAt
        }
    }

    private fun defaultTriggerName(descriptor: TriggerDescriptor): String{
        return "${descriptor.type}_${descriptor.expression.replace(" ", "_")}"

    }

}
