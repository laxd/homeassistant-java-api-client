package uk.laxd.homeassistantclient.ws

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.ws.HomeAssistantWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.MessageHandlerDelegate

@Component
class HomeAssistantWebSocketHandler implements WebSocketHandler {

    private messageBuilder = new StringBuilder()
    private final MessageHandlerDelegate messageHandler
    private final ObjectMapper objectMapper

    @Autowired
    HomeAssistantWebSocketHandler(MessageHandlerDelegate messageHandler, ObjectMapper objectMapper) {
        this.messageHandler = messageHandler
        this.objectMapper = objectMapper
        this.objectMapper.registerModule(new JavaTimeModule())
    }

    void afterConnectionEstablished(WebSocketSession session) {
        println "WebSocket Connection established"
    }

    void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (message.isLast()) {
            try {
                def payload = messageBuilder.isEmpty() ? message.payload.toString() : messageBuilder.append(message.payload.toString()).toString()

                def parsedMessage = objectMapper.readValue(payload, HomeAssistantWebSocketMessage.class)

                messageHandler.handle(session, parsedMessage)
            }
            catch (Exception e) {
                // Avoid propagating exception up to websocket, to avoid closing if a listener throws an exception
                println(e.message)
            }
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

