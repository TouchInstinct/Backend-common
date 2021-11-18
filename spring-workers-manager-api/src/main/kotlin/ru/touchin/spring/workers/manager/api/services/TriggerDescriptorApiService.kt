package ru.touchin.spring.workers.manager.api.services

import ru.touchin.spring.workers.manager.api.dto.TriggerChangeRequest

interface TriggerDescriptorApiService {

    fun update(workerName: String, triggerName: String, body: TriggerChangeRequest)
    fun create(workerName: String, body: TriggerChangeRequest)

}
