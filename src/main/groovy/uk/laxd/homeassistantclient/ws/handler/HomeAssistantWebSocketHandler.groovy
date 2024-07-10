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

    private final StringBuilder messageBuilder = new StringBuilder()
    private final ObjectMapper objectMapper
    private final MessageHandlerDelegate messageHandler

    @Autowired
    HomeAssistantWebSocketHandler(ObjectMapper objectMapper, MessageHandlerDelegate messageHandler) {
        this.objectMapper = objectMapper
        this.messageHandler = messageHandler
    }

    @Override
    void afterConnectionEstablished(WebSocketSession session) {
        log.info("WebSocket Connection established")
    }

    @Override
    void handleMessage(WebSocketSession session, WebSocketMessage<?> message) {
        messageBuilder.append(message.payload)

        if (message.last) {
            try {
                String payload = messageBuilder

                def parsedMessage = objectMapper.readValue(payload, IncomingWebSocketMessage)

                log.info("Received ${parsedMessage.class.simpleName}: $payload")

                messageHandler.handle(session, parsedMessage)
            }
            catch (Exception e) {
                // Avoid propagating exception up to websocket, to avoid closing if a listener throws an exception
                log.error("Encountered error while handling WebSocket message: {}", e.message)
            }
            finally {
                // Reset string builder now that a message has been processed
                messageBuilder.delete(0, messageBuilder.length())
            }
        }
    }

    @Override
    void handleTransportError(WebSocketSession session, Throwable exception) {
        log.error("WebSocket Error {}", exception)
    }

    @Override
    void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) {
        log.info("WebSocket connection closed: {}", closeStatus)
    }

    @Override
    boolean supportsPartialMessages() {
        true
    }

}
