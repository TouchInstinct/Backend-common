@file:Suppress("unused")
package ru.touchin.push.message.provider.mock

import org.springframework.context.annotation.Import
import ru.touchin.push.message.provider.mock.configurations.PushMessageProviderMockConfiguration

@Import(value = [PushMessageProviderMockConfiguration::class])
annotation class EnablePushMessageProviderMock
