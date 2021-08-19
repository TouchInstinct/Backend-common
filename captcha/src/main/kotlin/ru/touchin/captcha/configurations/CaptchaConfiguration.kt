package ru.touchin.captcha.configurations

import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.annotation.ComponentScan

@ComponentScan("ru.touchin.captcha")
@ConfigurationPropertiesScan("ru.touchin.captcha")
class CaptchaConfiguration
