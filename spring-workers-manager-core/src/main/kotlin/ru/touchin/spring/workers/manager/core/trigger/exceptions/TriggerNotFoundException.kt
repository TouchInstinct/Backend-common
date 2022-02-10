package ru.touchin.spring.workers.manager.core.trigger.exceptions

import ru.touchin.common.exceptions.CommonNotFoundException
import java.util.*

class TriggerNotFoundException : CommonNotFoundException {

    constructor(id: UUID) : super("TriggerDescriptor not found id=$id")
    constructor(name: String) : super("TriggerDescriptor code not found name=$name")

}
