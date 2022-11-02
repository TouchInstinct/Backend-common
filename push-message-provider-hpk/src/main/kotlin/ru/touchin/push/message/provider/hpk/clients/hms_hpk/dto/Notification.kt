package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto

import ru.touchin.push.message.provider.hpk.base.builders.Buildable

internal data class Notification private constructor(
    /** Title for notification. Must be specified here or in [AndroidNotificationConfig.title] */
    val title: String?,
    /** Text body for notification. Must be specified here or in [AndroidNotificationConfig.body] */
    val body: String?,
    /** Url of image */
    val image: String?,
) {

    class Validator {

        fun check(notification: Notification) {
            with(notification) {
                if (image != null) {
                    require(
                        image.matches(HTTPS_URL_PATTERN)
                    ) { "image's url should start with HTTPS" }
                }
            }
        }

        private companion object {

            val HTTPS_URL_PATTERN: Regex = Regex("^https.*")

        }

    }

    class Builder : Buildable {

        private var title: String? = null
        private var body: String? = null
        private var image: String? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setBody(body: String): Builder {
            this.body = body
            return this
        }

        fun setImage(image: String): Builder {
            this.image = image
            return this
        }

        fun build(): Notification {
            return Notification(
                title = title,
                body = body,
                image = image
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }
}
