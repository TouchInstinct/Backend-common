package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.web.WebDir
import java.util.*

internal data class WebNotification private constructor(
    val title: String?,
    val body: String?,
    val icon: String?,
    val image: String?,
    val lang: String?,
    val tag: String?,
    val badge: String?,
    @JsonProperty("dir")
    val webDir: WebDir?,
    val vibrate: Collection<Int>?,
    val renotify: Boolean,
    val requireInteraction: Boolean,
    val silent: Boolean,
    val timestamp: Long?,
    @JsonProperty("actions")
    val webActions: Collection<WebActions>?,
) {

    class Validator {

        fun check(webNotification: WebNotification) {
            // no verification required
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
        private var webDir: WebDir? = null
        private val vibrate: MutableList<Int> = mutableListOf()
        private var renotify = false
        private var requireInteraction = false
        private var silent = false
        private var timestamp: Long? = null
        private val webActions: MutableList<WebActions> = mutableListOf()

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setBody(body: String): Builder {
            this.body = body
            return this
        }

        fun setIcon(icon: String): Builder {
            this.icon = icon
            return this
        }

        fun setImage(image: String): Builder {
            this.image = image
            return this
        }

        fun setLang(lang: String): Builder {
            this.lang
            return this
        }

        fun setTag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun setBadge(badge: String): Builder {
            this.badge = badge
            return this
        }

        fun setDir(webDir: WebDir): Builder {
            this.webDir = webDir
            return this
        }

        fun addVibrate(vibrate: Int): Builder {
            this.vibrate.add(vibrate)
            return this
        }

        fun setRenotify(renotify: Boolean): Builder {
            this.renotify = renotify
            return this
        }

        fun setRequireInteraction(requireInteraction: Boolean): Builder {
            this.requireInteraction = requireInteraction
            return this
        }

        fun setSilent(silent: Boolean): Builder {
            this.silent = silent
            return this
        }

        fun setTimestamp(timestamp: Long): Builder {
            this.timestamp = timestamp
            return this
        }

        fun addActions(vararg webActions: WebActions): Builder {
            this.webActions.addAll(webActions)
            return this
        }

        fun build(): WebNotification {
            return WebNotification(
                title = title,
                body = body,
                icon = icon,
                image = image,
                lang = lang,
                tag = tag,
                badge = badge,
                webDir = webDir,
                vibrate = vibrate.takeIf(Collection<*>::isNotEmpty),
                renotify = renotify,
                requireInteraction = requireInteraction,
                silent = silent,
                timestamp = timestamp,
                webActions = webActions.takeIf(Collection<*>::isNotEmpty),
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
