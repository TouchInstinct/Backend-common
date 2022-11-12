package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web.WebHmsOptions
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web.WebNotification
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.web.WebPushHeaders

internal data class WebPushConfig private constructor(
    @JsonProperty("headers")
    val webPushHeaders: WebPushHeaders?,
    val data: String?,
    @JsonProperty("notification")
    val webNotification: WebNotification?,
    @JsonProperty("hms_options")
    val webHmsOptions: WebHmsOptions?,
) {

    class Validator {

        fun check(webPushConfig: WebPushConfig) {
            with(webPushConfig) {
                webPushHeaders?.let { WebPushHeaders.validator.check(it) }
                webNotification?.let { WebNotification.validator.check(it) }
                webHmsOptions?.let { WebHmsOptions.validator.check(it) }
            }
        }

    }

    class Builder : Buildable {

        private var webPushHeaders: WebPushHeaders? = null
        private var data: String? = null
        private var webNotification: WebNotification? = null
        private var webHmsOptions: WebHmsOptions? = null

        fun setHeaders(webPushHeaders: WebPushHeaders): Builder {
            this.webPushHeaders = webPushHeaders
            return this
        }

        fun setData(data: String): Builder {
            this.data = data
            return this
        }

        fun setNotification(webNotification: WebNotification): Builder {
            this.webNotification = webNotification
            return this
        }

        fun setWebHmsOptions(webHmsOptions: WebHmsOptions): Builder {
            this.webHmsOptions = webHmsOptions
            return this
        }

        fun build(): WebPushConfig {
            return WebPushConfig(
                webPushHeaders = webPushHeaders,
                data = data,
                webNotification = webNotification,
                webHmsOptions = webHmsOptions,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
