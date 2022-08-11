@file:Suppress("unused")
package ru.touchin.push.message.provider.fcm

import org.springframework.context.annotation.Import
import ru.touchin.push.message.provider.fcm.configurations.PushMessageProviderFcmConfiguration

@Import(value = [PushMessageProviderFcmConfiguration::class])
annotation class EnablePushMessageProviderFcm
