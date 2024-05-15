package uk.laxd.homeassistantclient.ws

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerHandler
import uk.laxd.homeassistantclient.model.Event
import uk.laxd.homeassistantclient.model.ws.HomeAssistantWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.MessageHandlerDelegate

@Component
class HomeAssistantWebSocketHandler implements WebSocketHandler {

    def messageBuilder = new StringBuilder()

    @Autowired
    HomeAssistantEventListenerHandler listenerHandler

    @Autowired
    MessageHandlerDelegate messageHandler

    void afterConnectionEstablished(WebSocketSession session) {
        println "WebSocket Connection established"
    }

    void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (message.isLast()) {
            def payload = messageBuilder.isEmpty() ? message.payload.toString() : messageBuilder.append(message.payload.toString()).toString()
            println payload

            def parsedMessage = new ObjectMapper().readValue(payload, HomeAssistantWebSocketMessage.class)
            messageHandler.handle(session, parsedMessage)

//            listenerHandler.dispatchMessage(event)
        } else {
            messageBuilder.append(message.payload)
        }
    }

    void handleTransportError(WebSocketSession session, Throwable exception) {
        println "WebSocket Error: $exception"
    }

    void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        println "WebSocket Connection closed $closeStatus"
    }

    boolean supportsPartialMessages() {
        return true
    }

}

