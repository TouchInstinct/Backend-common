package ru.touchin.push.message.provider.hpk

import org.springframework.context.annotation.Import
import ru.touchin.push.message.provider.hpk.configurations.PushMessageProviderHpkConfiguration

@Import(value = [PushMessageProviderHpkConfiguration::class])
annotation class EnablePushMessageProviderHpk
