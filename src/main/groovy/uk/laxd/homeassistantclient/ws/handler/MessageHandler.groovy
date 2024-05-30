package uk.laxd.homeassistantclient.ws.handler

import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage

interface MessageHandler<M extends IncomingWebSocketMessage> {

    void handle(WebSocketSession session, M message)
    boolean canHandle(IncomingWebSocketMessage message)

}