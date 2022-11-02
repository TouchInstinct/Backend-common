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

        private var headers: WebPushHeaders? = null
        private var data: String? = null
        private var notification: WebNotification? = null
        private var webHmsOptions: WebHmsOptions? = null

        fun build(): WebPushConfig {
            return WebPushConfig(
                webPushHeaders = headers,
                data = data,
                webNotification = notification,
                webHmsOptions = webHmsOptions,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
