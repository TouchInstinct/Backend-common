package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web

import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class WebActions private constructor(
    val action: String?,
    val icon: String?,
    val title: String?,
) {

    class Validator {

        fun check() {
            // no validation
        }

    }

    class Builder : Buildable {

        private var action: String? = null
        private var icon: String? = null
        private var title: String? = null

        fun setAction(action: String): Builder {
            this.action = action
            return this
        }

        fun setIcon(icon: String): Builder {
            this.icon = icon
            return this
        }

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun build(): WebActions {
            return WebActions(
                action = action,
                icon = icon,
                title = title,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
