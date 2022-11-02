package ru.touchin.push.message.provider.hpk.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.net.URL
import java.time.Duration

@ConstructorBinding
@ConfigurationProperties(prefix = "push-message-provider.hpk")
data class HpkProperties(
    val webServices: WebServices,
) {

    class WebServices(
        val clientId: String,
        val oauth: Oauth,
        val hpk: Hpk,
    )

    class Oauth(
        val clientSecret: String,
        url: URL,
        http: Http,
        ssl: Ssl?,
    ) : WebService(
        url = url,
        http = http,
        ssl = ssl,
    )

    class Hpk(
        url: URL,
        http: Http,
        ssl: Ssl?,
    ) : WebService(
        url = url,
        http = http,
        ssl = ssl,
    )

    open class WebService(
        val url: URL,
        val http: Http,
        val ssl: Ssl?,
    )

    class Http(
        val readTimeout: Duration,
        val writeTimeout: Duration,
        val connectionTimeout: Duration,
    )

    class Ssl(
        val handshakeTimeout: Duration,
        val notifyFlushTimeout: Duration,
        val notifyReadTimeout: Duration,
    )

}
