package ru.touchin.spring.workers.manager.api.controllers

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.spring.workers.manager.api.dto.TriggerChangeRequest
import ru.touchin.spring.workers.manager.api.services.TriggerDescriptorApiService

@RestController
@RequestMapping("/workers/{workerName}/triggers")
class TriggerController(
    private val triggerDescriptorApiService: TriggerDescriptorApiService
) {

    @PostMapping
    fun createTrigger(
        @PathVariable
        workerName: String,
        @RequestBody
        body: TriggerChangeRequest
    ) {
        triggerDescriptorApiService.create(workerName, body)
    }

    @PutMapping("/{triggerName}")
    fun changeTrigger(
        @PathVariable
        triggerName: String,
        @PathVariable
        workerName: String,
        @RequestBody
        body: TriggerChangeRequest
    ) {
        triggerDescriptorApiService.update(workerName, triggerName, body)
    }

}

