package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import java.util.*

internal data class WebNotification private constructor(
    val title: String?,
    val body: String?,
    val icon: String?,
    val image: String?,
    val lang: String?,
    val tag: String?,
    val badge: String?,
    val dir: String?,
    val vibrate: Collection<Int>?,
    val renotify: Boolean,
    val requireInteraction: Boolean,
    val silent: Boolean,
    val timestamp: Long?,
    @JsonProperty("actions")
    val actions: Collection<WebActions>?,
) {

    class Validator {

        fun check(webNotification: WebNotification) {
            with(webNotification) {
                if (dir != null) {
                    require(
                        DIR_VALUE.any { it == dir }
                    ) { "Invalid dir" }
                }
            }
        }

        private companion object {

            val DIR_VALUE: Array<String> = arrayOf("auto", "ltr", "rtl")

        }

    }

    class Builder : Buildable {

        private var title: String? = null
        private var body: String? = null
        private var icon: String? = null
        private var image: String? = null
        private var lang: String? = null
        private var tag: String? = null
        private var badge: String? = null
        private var dir: String? = null
        private val vibrate: MutableList<Int> = mutableListOf()
        private var renotify = false
        private var requireInteraction = false
        private var silent = false
        private var timestamp: Long? = null
        private val actions: MutableList<WebActions> = mutableListOf()

        fun build(): WebNotification {
            return WebNotification(
                title = title,
                body = body,
                icon = icon,
                image = image,
                lang = lang,
                tag = tag,
                badge = badge,
                dir = dir,
                vibrate = vibrate.takeIf(Collection<*>::isNotEmpty),
                renotify = renotify,
                requireInteraction = requireInteraction,
                silent = silent,
                timestamp = timestamp,
                actions = actions.takeIf(Collection<*>::isNotEmpty),
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
