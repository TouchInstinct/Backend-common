package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web

import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import java.net.MalformedURLException
import java.net.URL

internal data class WebHmsOptions private constructor(
    val link: String?,
) {

    class Validator {

        fun check(webHmsOptions: WebHmsOptions) {
            with(webHmsOptions) {
                if (!link.isNullOrBlank()) {
                    try {
                        URL(link)
                    } catch (e: MalformedURLException) {
                        require(false) { "Invalid link" }
                    }
                }
            }
        }

    }

    class Builder : Buildable {

        private var link: String? = null

        fun build(): WebHmsOptions {
            return WebHmsOptions(
                link = link,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
