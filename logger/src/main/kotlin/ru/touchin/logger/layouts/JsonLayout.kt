package ru.touchin.logger.layouts

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.contrib.json.classic.JsonLayout as ContribJsonLayout

private const val MESSAGE_TYPE_ATTR_NAME = "messageType"

@Suppress("unused", "MemberVisibilityCanBePrivate")
class JsonLayout : ContribJsonLayout() {

    var messageType: String? = null

    override fun addCustomDataToJsonMap(map: MutableMap<String, Any>?, event: ILoggingEvent?) {
        messageType?.let {
            map?.put(MESSAGE_TYPE_ATTR_NAME, it)
        }
    }

}
