package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.apns

import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import ru.touchin.push.message.provider.hpk.base.builders.Buildable

@JsonNaming(PropertyNamingStrategies.KebabCaseStrategy::class)
internal data class ApnsAlert private constructor(
    val title: String?,
    val body: String?,
    val titleLocKey: String?,
    val titleLocArgs: Collection<String>?,
    val actionLocKey: String?,
    val locKey: String?,
    val locArgs: Collection<String>?,
    val launchImage: String?,
) {

    class Validator {

        fun check(apnsAlert: ApnsAlert) {
            with(apnsAlert) {
                if (!locArgs.isNullOrEmpty()) {
                    require(!locKey.isNullOrBlank()) { "locKey is required when specifying locArgs" }
                }
                if (!titleLocArgs.isNullOrEmpty()) {
                    require(!titleLocKey.isNullOrBlank()) { "titleLocKey is required when specifying titleLocArgs" }
                }
            }
        }

    }

    class Builder : Buildable {

        private var title: String? = null
        private var body: String? = null
        private var titleLocKey: String? = null
        private val titleLocArgs: MutableList<String> = mutableListOf()
        private var actionLocKey: String? = null
        private var locKey: String? = null
        private val locArgs: MutableList<String> = mutableListOf()
        private var launchImage: String? = null

        fun setTitle(title: String): Builder {
            this.title = title
            return this
        }

        fun setBody(body: String): Builder {
            this.body = body
            return this
        }

        fun setTitleLocKey(titleLocKey: String): Builder {
            this.titleLocKey = titleLocKey
            return this
        }

        fun setAddTitleLocArg(vararg titleLocArg: String): Builder {
            titleLocArgs.addAll(titleLocArg)
            return this
        }

        fun setActionLocKey(actionLocKey: String): Builder {
            this.actionLocKey = actionLocKey
            return this
        }

        fun setLocKey(locKey: String): Builder {
            this.locKey = locKey
            return this
        }

        fun addAllLocArgs(vararg locArgs: String): Builder {
            this.locArgs.addAll(locArgs)
            return this
        }

        fun setLaunchImage(launchImage: String): Builder {
            this.launchImage = launchImage
            return this
        }

        fun build(): ApnsAlert {
            return ApnsAlert(
                title = title,
                body = body,
                titleLocKey = titleLocKey,
                titleLocArgs = titleLocArgs.takeIf(Collection<*>::isNotEmpty),
                actionLocKey = actionLocKey,
                locKey = locKey,
                locArgs = locArgs.takeIf(Collection<*>::isNotEmpty),
                launchImage = launchImage,
            )
        }
    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
