package uk.laxd.homeassistantclient.ws.handler

import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage

interface MessageHandler<M extends HomeAssistantWebSocketMessage> {

    void handle(WebSocketSession session, M message)
    boolean canHandle(HomeAssistantWebSocketMessage message)

}