package uk.laxd.homeassistantclient.client

import jakarta.inject.Singleton
import org.springframework.stereotype.Component

@Component
@Singleton
class HomeAssistantAuthentication {
    String url
    String token
}