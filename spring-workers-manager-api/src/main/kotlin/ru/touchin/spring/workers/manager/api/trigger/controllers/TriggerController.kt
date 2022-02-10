package ru.touchin.spring.workers.manager.api.trigger.controllers

import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.spring.workers.manager.api.trigger.controllers.dto.TriggerChangeRequest
import ru.touchin.spring.workers.manager.api.trigger.services.TriggerDescriptorApiService
import ru.touchin.spring.workers.manager.api.trigger.services.dto.CreateTrigger
import ru.touchin.spring.workers.manager.api.trigger.services.dto.UpdateTrigger

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
        triggerDescriptorApiService.create(
            CreateTrigger(
                workerName = workerName,
                triggerName = body.name,
                type = body.type,
                expression = body.expression,
                disabled = body.disabled,
            )
        )
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
        triggerDescriptorApiService.update(
            UpdateTrigger(
                workerName = workerName,
                oldTriggerName = triggerName,
                newTriggerName = body.name,
                type = body.type,
                expression = body.expression,
                disabled = body.disabled,
            )
        )
    }

}

