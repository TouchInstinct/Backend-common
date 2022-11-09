package ru.touchin.push.message.provider.mock.factories

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Service
import ru.touchin.logger.dto.LogData
import ru.touchin.logger.factory.LogBuilderFactory
import ru.touchin.push.message.provider.dto.request.PushTokenCheck
import ru.touchin.push.message.provider.dto.request.SendPushRequest
import ru.touchin.push.message.provider.dto.result.CheckPushTokenResult
import ru.touchin.push.message.provider.dto.result.SendPushResult
import ru.touchin.push.message.provider.dto.result.SendPushTokenMessageResult
import ru.touchin.push.message.provider.enums.PlatformType
import ru.touchin.push.message.provider.enums.PushMessageProviderType
import ru.touchin.push.message.provider.enums.PushTokenStatus
import ru.touchin.push.message.provider.factories.PushMessageProviderServiceFactory
import ru.touchin.push.message.provider.mock.properties.PushMessageProviderMockProperties
import ru.touchin.push.message.provider.services.PushMessageProviderService
import java.util.*

@Primary
@Service
@ConditionalOnProperty(prefix = "push-message-provider", name = ["mock.enabled"], havingValue = "true")
class PushMessageProviderMockServiceFactoryImpl(
    private val logBuilderFactory: LogBuilderFactory<LogData>,
    private val pushMessageProviderMockProperties: PushMessageProviderMockProperties,
) : PushMessageProviderServiceFactory {

    override fun get(platformType: PlatformType): PushMessageProviderService {
        return object : PushMessageProviderService {
            override val type: PushMessageProviderType
                get() = PushMessageProviderType.FCM

            override fun send(request: SendPushRequest): SendPushResult {
                val millis = pushMessageProviderMockProperties.sleepFor.toMillis()

                logBuilderFactory.create(this::class.java)
                    .addTags("MOCK_PUSH_SEND")
                    .setMethod("send")
                    .addData("sleepForMs" to millis)
                    .build()
                    .log()

                Thread.sleep(millis)

                return SendPushTokenMessageResult(
                    messageId = UUID.randomUUID().toString(),
                )
            }

            override fun check(request: PushTokenCheck): CheckPushTokenResult {
                return CheckPushTokenResult(PushTokenStatus.VALID)
            }

        }
    }

}
