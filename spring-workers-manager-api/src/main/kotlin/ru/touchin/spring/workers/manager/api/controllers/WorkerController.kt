package ru.touchin.spring.workers.manager.api.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import ru.touchin.spring.workers.manager.api.dto.WorkerDto
import ru.touchin.spring.workers.manager.api.services.WorkerApiService
import ru.touchin.spring.workers.manager.core.services.WorkersStateService

@RestController
@RequestMapping("/workers")
class WorkerController(
    private val workerStateService: WorkersStateService,
    private val workerApiService: WorkerApiService
) {

    @GetMapping
    fun getWorkers(): List<WorkerDto> {
        return workerApiService.getWorkers()
    }

    @GetMapping("/{workerName}")
    fun getWorker(
        @PathVariable
        workerName: String
    ): WorkerDto {
        return workerApiService.getWorker(workerName)
    }

    @PostMapping("/{workerName}/stop")
    fun stop(
        @PathVariable
        workerName: String
    ) {
        workerStateService.stop(workerName)
    }

    @PostMapping("/{workerName}/start")
    fun start(
        @PathVariable
        workerName: String
    ) {
        workerStateService.start(workerName)
    }

    @PostMapping("/{workerName}/disable")
    fun disable(
        @PathVariable
        workerName: String
    ) {
        workerStateService.disable(workerName)
    }

    @PostMapping("/{workerName}/enable")
    fun enable(
        @PathVariable
        workerName: String
    ) {
        workerStateService.enable(workerName)
    }

}

