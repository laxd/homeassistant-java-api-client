package uk.laxd.homeassistantclient.ws.handler

import com.fasterxml.jackson.databind.ObjectMapper
import groovy.util.logging.Slf4j
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.CloseStatus
import org.springframework.web.socket.WebSocketHandler
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage

@Component
@Slf4j
class HomeAssistantWebSocketHandler implements WebSocketHandler {

    private messageBuilder = new StringBuilder()
    private final ObjectMapper objectMapper
    private final MessageHandlerDelegate messageHandler

    @Autowired
    HomeAssistantWebSocketHandler(ObjectMapper objectMapper, MessageHandlerDelegate messageHandler) {
        this.objectMapper = objectMapper
        this.messageHandler = messageHandler
    }

    void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket Connection established")
    }

    void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        if (message.isLast()) {
            try {
                def payload = messageBuilder.isEmpty() ? message.payload.toString() : messageBuilder.append(message.payload.toString()).toString()

                def parsedMessage = objectMapper.readValue(payload, IncomingWebSocketMessage.class)

                log.info("Received message: {}", payload)

                messageHandler.handle(session, parsedMessage)
            }
            catch (Exception e) {
                // Avoid propagating exception up to websocket, to avoid closing if a listener throws an exception
                log.error("Encountered error while handling WebSocket message: {}", e.message)
            }
        } else {
            messageBuilder.append(message.payload)
        }
    }

    void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WebSocket Error {}", exception)
    }

    void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("WebSocket connection closed: {}", closeStatus)
    }

    boolean supportsPartialMessages() {
        return true
    }

}

