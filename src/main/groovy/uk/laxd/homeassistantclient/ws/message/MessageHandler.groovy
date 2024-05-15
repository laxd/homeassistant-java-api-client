package uk.laxd.homeassistantclient.ws.message

import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.ws.HomeAssistantWebSocketMessage

interface MessageHandler<M extends HomeAssistantWebSocketMessage> {

    void handle(WebSocketSession session, M message)
    boolean canHandle(HomeAssistantWebSocketMessage message)

}

