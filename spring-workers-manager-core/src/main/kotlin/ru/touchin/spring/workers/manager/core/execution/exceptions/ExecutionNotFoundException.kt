package ru.touchin.spring.workers.manager.core.execution.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException
import java.util.*

class ExecutionNotFoundException(id: UUID) : CommonNotFoundException("Execution not found id=$id")
