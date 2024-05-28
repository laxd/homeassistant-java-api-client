package uk.laxd.homeassistantclient.ws.handler

import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.AbstractWebSocketHandler

class AuthenticationWebSocketMessageHandler extends AbstractWebSocketHandler {

    @Override
    void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        super.handleMessage(session, message)
    }
}
