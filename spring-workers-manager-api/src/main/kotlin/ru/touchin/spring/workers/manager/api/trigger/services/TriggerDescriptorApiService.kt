package ru.touchin.spring.workers.manager.api.trigger.services

import ru.touchin.spring.workers.manager.api.trigger.services.dto.CreateTrigger
import ru.touchin.spring.workers.manager.api.trigger.services.dto.UpdateTrigger

interface TriggerDescriptorApiService {

    fun create(create: CreateTrigger)
    fun update(update: UpdateTrigger)

}
