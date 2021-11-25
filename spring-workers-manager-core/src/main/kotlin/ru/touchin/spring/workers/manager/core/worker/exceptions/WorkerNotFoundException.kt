package ru.touchin.spring.workers.manager.core.worker.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException

class WorkerNotFoundException(name: String): CommonNotFoundException(
    "Worker not found name=$name"
)
