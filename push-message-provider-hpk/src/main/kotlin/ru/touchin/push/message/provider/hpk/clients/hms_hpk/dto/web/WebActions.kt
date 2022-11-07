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
