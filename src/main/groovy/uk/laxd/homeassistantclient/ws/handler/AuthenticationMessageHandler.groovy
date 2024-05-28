package uk.laxd.homeassistantclient.ws.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.client.HomeAssistantAuthenticationProvider
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.WebSocketAuthenticationSync

@Component
class AuthenticationMessageHandler implements MessageHandler<HomeAssistantAuthRequiredMessage> {

    HomeAssistantAuthenticationProvider authenticationProvider
    WebSocketAuthenticationSync sync

    @Autowired
    AuthenticationMessageHandler(HomeAssistantAuthenticationProvider authenticationProvider, WebSocketAuthenticationSync sync) {
        this.authenticationProvider = authenticationProvider
        this.sync = sync
    }

    @Override
    void handle(WebSocketSession session, HomeAssistantAuthRequiredMessage message) throws Exception {
        // Authentication is required, send auth message
        session.sendMessage(
            new TextMessage("""
            {
                "type": "auth",
                "access_token": "${authenticationProvider.authentication.token}"
            }
            """)
        )
    }

    boolean canHandle(HomeAssistantWebSocketMessage message) {
        return message instanceof HomeAssistantAuthRequiredMessage
    }
}