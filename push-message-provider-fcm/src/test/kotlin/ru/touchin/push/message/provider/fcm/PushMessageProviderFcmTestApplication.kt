package ru.touchin.push.message.provider.fcm

import com.google.firebase.FirebaseApp
import org.springframework.boot.SpringBootConfiguration
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent

@TestConfiguration
@SpringBootConfiguration
@EnablePushMessageProviderFcm
class PushMessageProviderFcmTestApplication : ApplicationListener<ContextRefreshedEvent> {

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        clearSingletonsOutsideContainer()
    }

    private fun clearSingletonsOutsideContainer() {
        FirebaseApp.getApps().forEach(FirebaseApp::delete)
    }

}
