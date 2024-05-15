package uk.laxd.homeassistantclient.ws.message

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.client.HomeAssistantAuthentication
import uk.laxd.homeassistantclient.model.ws.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.ws.HomeAssistantWebSocketMessage

@Component
class AuthenticationMessageHandler implements MessageHandler<HomeAssistantAuthRequiredMessage> {

    HomeAssistantAuthentication auth

    @Autowired
    AuthenticationMessageHandler(HomeAssistantAuthentication auth) {
        this.auth = auth
    }

    void handle(WebSocketSession session, HomeAssistantAuthRequiredMessage message) {
        session.sendMessage(
            new TextMessage("""
            {
                "type": "auth",
                "access_token": "${auth.token}"
            }
            """)
        )
    }

    boolean canHandle(HomeAssistantWebSocketMessage message) {
        return message instanceof HomeAssistantAuthRequiredMessage
    }
}