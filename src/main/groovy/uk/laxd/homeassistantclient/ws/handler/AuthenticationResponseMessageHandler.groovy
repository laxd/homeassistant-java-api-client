package uk.laxd.homeassistantclient.ws.handler

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthSuccessfulMessage

import uk.laxd.homeassistantclient.ws.message.WebSocketAuthenticationSync

@Component
class AuthenticationResponseMessageHandler implements MessageHandler<HomeAssistantAuthSuccessfulMessage> {

    private WebSocketAuthenticationSync sync

    @Autowired
    AuthenticationResponseMessageHandler(WebSocketAuthenticationSync sync) {
        this.sync = sync
    }

    @Override
    void handle(WebSocketSession session, HomeAssistantAuthSuccessfulMessage message) throws Exception {
        synchronized (sync.getSync()) {
            sync.authenticated = true
            sync.getSync().notify()
        }
    }

    boolean canHandle(IncomingWebSocketMessage message) {
        return message instanceof HomeAssistantAuthSuccessfulMessage
    }
}