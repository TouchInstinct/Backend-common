package ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.android

import com.fasterxml.jackson.annotation.JsonProperty
import ru.touchin.push.message.provider.hpk.base.builders.Buildable
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.dto.Notification
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidImportance
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidStyleType
import ru.touchin.push.message.provider.hpk.clients.hms_hpk.enums.android.AndroidVisibility

internal data class AndroidNotificationConfig private constructor(
    /**
     * Title of an Android notification message.
     * If the title parameter is set, the value of the [Notification.title] field is overwritten.
     * */
    val title: String?,
    /**
     * Body of an Android notification message.
     * If the body parameter is set, the value of the [Notification.body] field is overwritten.
     * */
    val body: String?,
    /**
     * Custom app icon on the left of a notification message.
     */
    val icon: String?,
    /** Custom notification bar button color. */
    val color: String?,
    val sound: String?,
    /** Indicates whether to use the default ringtone. */
    val defaultSound: Boolean,
    /**
     * Message tag.
     * Messages that use the same message tag in the same app will be overwritten by the latest message.
     * */
    val tag: String?,
    @JsonProperty("click_action")
    val androidClickAction: AndroidClickAction?,
    val bodyLocKey: String?,
    val bodyLocArgs: Collection<String>?,
    val titleLocKey: String?,
    val titleLocArgs: Collection<String>?,
    val multiLangKey: Map<String, String>?,
    /** Custom channel for displaying notification messages. */
    val channelId: String?,
    /** Brief description of a notification message to an Android app. */
    val notifySummary: String?,
    /** URL of the custom small image on the right of a notification message. */
    val image: String?,
    /** Notification bar style. */
    @JsonProperty("style")
    val androidStyleType: AndroidStyleType?,
    /** Android notification message title in large text style. */
    val bigTitle: String?,
    val bigBody: String?,
    /**
     * Unique notification ID of a message.
     * If a message does not contain the ID or the ID is -1, the NC will generate a unique ID for the message.
     * Different notification messages can use the same notification ID, so that new messages can overwrite old messages.
     * */
    val notifyId: Int?,
    /**
     * Message group.
     * For example, if 10 messages that contain the same value of group are sent to a device,
     * the device displays only the latest message and the total number of messages received in the group,
     * but does not display these 10 messages.
     */
    val group: String?,
    @JsonProperty("badge")
    val androidBadgeNotification: AndroidBadgeNotification? = null,
    val autoCancel: Boolean,
    /**
     * Time when Android notification messages are delivered, in the UTC timestamp format.
     * If you send multiple messages at the same time,
     * they will be sorted based on this value and displayed in the Android notification panel.
     * Example: 2014-10-02T15:01:23.045123456Z
     */
    @JsonProperty("when")
    val sendAt: String?,
    val localOnly: Boolean? = null,
    /**
     * Android notification message priority, which determines the message notification behavior of a user device.
     */
    @JsonProperty("importance")
    val androidImportance: AndroidImportance?,
    /** Indicates whether to use the default vibration mode. */
    val useDefaultVibrate: Boolean,
    /** Indicates whether to use the default breathing light. */
    val useDefaultLight: Boolean,
    val vibrateConfig: Collection<String>?,
    /** Android notification message visibility. */
    @JsonProperty("visibility")
    val androidVisibility: AndroidVisibility?,
    @JsonProperty("light_settings")
    val androidLightSettings: AndroidLightSettings?,
    /**
     * Indicates whether to display notification messages in the NC when your app is running in the foreground.
     * If this parameter is not set, the default value true will be used,
     * indicating that notification messages will be displayed in the NC when your app runs in the foreground.
     * */
    val foregroundShow: Boolean,
    val inboxContent: Collection<String>?,
    @JsonProperty("buttons")
    val androidButtons: Collection<AndroidButton>?,
    /** ID of the user-app relationship. */
    val profileId: String?,
) {

    class Validator {

        fun check(androidNotificationConfig: AndroidNotificationConfig, notification: Notification?) {
            with(androidNotificationConfig) {
                androidBadgeNotification?.let { AndroidBadgeNotification.validator.check(it) }
                androidLightSettings?.also { AndroidLightSettings.validator.check(it) }
                androidClickAction?.also { AndroidClickAction.validator.check(it) }

                require(!notification?.title.isNullOrBlank() || !title.isNullOrBlank()) { "title should be set" }
                require(!notification?.body.isNullOrBlank() || !body.isNullOrBlank()) { "body should be set" }

                if (!color.isNullOrBlank()) {
                    require(color.matches(COLOR_PATTERN)) { "Wrong color format, color must be in the form #RRGGBB" }
                }
                if (!image.isNullOrBlank()) {
                    require(image.startsWith(HTTPS_URL_PATTERN)) { "notifyIcon must start with $HTTPS_URL_PATTERN" }
                }
                if (androidStyleType != null) {
                    when (androidStyleType) {
                        AndroidStyleType.DEFAULT -> {
                            // no verification
                        }

                        AndroidStyleType.BIG_TEXT -> {
                            require(
                                !bigTitle.isNullOrBlank() && !bigBody.isNullOrBlank()
                            ) { "title and body are required when style is $androidStyleType" }
                        }

                        AndroidStyleType.INBOX -> {
                            require(
                                !inboxContent.isNullOrEmpty()
                            ) { "inboxContent is required when style is $androidStyleType" }
                            require(
                                inboxContent.size <= INBOX_CONTENT_MAX_ITEMS
                            ) { "inboxContent must have at most $INBOX_CONTENT_MAX_ITEMS items" }
                        }
                    }
                }
                if (profileId != null) {
                    require(
                        profileId.length <= PROFILE_ID_MAX_LENGTH
                    ) { "profileId length cannot exceed $PROFILE_ID_MAX_LENGTH characters" }
                }
            }
        }

        private companion object {

            val COLOR_PATTERN: Regex = Regex("^#[0-9a-fA-F]{6}$")
            const val HTTPS_URL_PATTERN: String = "https"
            const val INBOX_CONTENT_MAX_ITEMS: Byte = 5
            const val PROFILE_ID_MAX_LENGTH: Byte = 64

        }

    }

    class Builder : Buildable {

        private var title: String? = null
        private val body: String? = null
        private var icon: String? = null
        private var color: String? = null
        private var sound: String? = null
        private var defaultSound = false
        private var tag: String? = null
        private var bodyLocKey: String? = null
        private val bodyLocArgs: MutableList<String> = mutableListOf()
        private var titleLocKey: String? = null
        private val titleLocArgs: MutableList<String> = mutableListOf()
        private var multiLangkey: Map<String, String>? = null
        private var channelId: String? = null
        private var notifySummary: String? = null
        private var image: String? = null
        private var androidStyleType: AndroidStyleType? = null
        private var bigTitle: String? = null
        private var bigBody: String? = null
        private var notifyId: Int? = null
        private var group: String? = null
        private var androidBadgeNotification: AndroidBadgeNotification? = null
        private var autoCancel = true
        private var sendAt: String? = null
        private var androidImportance: AndroidImportance? = null
        private var useDefaultVibrate = false
        private var useDefaultLight = false
        private val vibrateConfig: MutableList<String> = mutableListOf()
        private var androidVisibility: AndroidVisibility? = null
        private var androidLightSettings: AndroidLightSettings? = null
        private var foregroundShow = false
        private val inboxContent: MutableList<String> = mutableListOf()
        private val buttons: MutableList<AndroidButton> = mutableListOf()
        private var profileId: String? = null

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

        fun setColor(color: String): Builder {
            this.color = color
            return this
        }

        fun setSound(sound: String): Builder {
            this.sound = sound
            return this
        }

        fun setDefaultSound(defaultSound: Boolean): Builder {
            this.defaultSound = defaultSound
            return this
        }

        fun setTag(tag: String): Builder {
            this.tag = tag
            return this
        }

        fun setBodyLocKey(bodyLocKey: String): Builder {
            this.bodyLocKey = bodyLocKey
            return this
        }

        fun addBodyLocArgs(vararg arg: String): Builder {
            bodyLocArgs.addAll(arg)
            return this
        }

        fun setTitleLocKey(titleLocKey: String): Builder {
            this.titleLocKey = titleLocKey
            return this
        }

        fun addTitleLocArgs(vararg args: String): Builder {
            titleLocArgs.addAll(args)
            return this
        }

        fun setMultiLangkey(multiLangkey: Map<String, String>): Builder {
            this.multiLangkey = multiLangkey
            return this
        }

        fun setChannelId(channelId: String): Builder {
            this.channelId = channelId
            return this
        }

        fun setNotifySummary(notifySummary: String): Builder {
            this.notifySummary = notifySummary
            return this
        }

        fun setImage(image: String): Builder {
            this.image = image
            return this
        }

        fun setStyle(androidStyleType: AndroidStyleType): Builder {
            this.androidStyleType = androidStyleType
            return this
        }

        fun setBigTitle(bigTitle: String): Builder {
            this.bigTitle = bigTitle
            return this
        }

        fun setBigBody(bigBody: String): Builder {
            this.bigBody = bigBody
            return this
        }

        fun setNotifyId(notifyId: Int): Builder {
            this.notifyId = notifyId
            return this
        }

        fun setGroup(group: String): Builder {
            this.group = group
            return this
        }

        fun setBadge(androidBadgeNotification: AndroidBadgeNotification): Builder {
            this.androidBadgeNotification = androidBadgeNotification
            return this
        }

        fun setAutoCancel(autoCancel: Boolean): Builder {
            this.autoCancel = autoCancel
            return this
        }

        fun sendAt(sendAt: String): Builder {
            this.sendAt = sendAt
            return this
        }

        fun setImportance(androidImportance: AndroidImportance): Builder {
            this.androidImportance = androidImportance
            return this
        }

        fun setUseDefaultVibrate(useDefaultVibrate: Boolean): Builder {
            this.useDefaultVibrate = useDefaultVibrate
            return this
        }

        fun setUseDefaultLight(useDefaultLight: Boolean): Builder {
            this.useDefaultLight = useDefaultLight
            return this
        }

        fun addVibrateConfig(vararg vibrateTimings: String): Builder {
            vibrateConfig.addAll(vibrateTimings)
            return this
        }

        fun setAndroidVisibility(androidVisibility: AndroidVisibility): Builder {
            this.androidVisibility = androidVisibility
            return this
        }

        fun setLightSettings(androidLightSettings: AndroidLightSettings): Builder {
            this.androidLightSettings = androidLightSettings
            return this
        }

        fun setForegroundShow(foregroundShow: Boolean): Builder {
            this.foregroundShow = foregroundShow
            return this
        }

        fun addInboxContent(vararg inboxContent: String): Builder {
            this.inboxContent.addAll(inboxContent)
            return this
        }

        fun addButton(vararg button: AndroidButton): Builder {
            buttons.addAll(button)
            return this
        }

        fun setProfileId(profileId: String): Builder {
            this.profileId = profileId
            return this
        }

        fun build(
            androidClickAction: AndroidClickAction,
        ): AndroidNotificationConfig {
            return AndroidNotificationConfig(
                title = title,
                body = body,
                icon = icon,
                color = color,
                sound = sound,
                defaultSound = defaultSound,
                tag = tag,
                androidClickAction = androidClickAction,
                bodyLocKey = bodyLocKey,
                bodyLocArgs = bodyLocArgs.takeIf(Collection<*>::isNotEmpty),
                titleLocKey = titleLocKey,
                titleLocArgs = titleLocArgs.takeIf(Collection<*>::isNotEmpty),
                multiLangKey = multiLangkey,
                channelId = channelId,
                notifySummary = notifySummary,
                image = image,
                androidStyleType = androidStyleType,
                bigTitle = bigTitle,
                bigBody = bigBody,
                notifyId = notifyId,
                group = group,
                androidBadgeNotification = androidBadgeNotification,
                autoCancel = autoCancel,
                sendAt = sendAt,
                androidImportance = androidImportance,
                useDefaultVibrate = useDefaultVibrate,
                useDefaultLight = useDefaultLight,
                vibrateConfig = vibrateConfig.takeIf(Collection<*>::isNotEmpty),
                androidVisibility = androidVisibility,
                androidLightSettings = androidLightSettings,
                foregroundShow = foregroundShow,
                inboxContent = inboxContent.takeIf(Collection<*>::isNotEmpty),
                androidButtons = buttons.takeIf(Collection<*>::isNotEmpty),
                profileId = profileId,
            )
        }

    }

    companion object {

        val validator = Validator()

        fun builder() = Builder()

    }

}
