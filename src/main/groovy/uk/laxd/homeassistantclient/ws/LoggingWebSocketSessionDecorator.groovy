package uk.laxd.homeassistantclient.ws

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.WebSocketSessionDecorator

class LoggingWebSocketSessionDecorator extends WebSocketSessionDecorator {
    private static final Logger logger = LoggerFactory.getLogger(LoggingWebSocketSessionDecorator.class)

    LoggingWebSocketSessionDecorator(WebSocketSession session) {
        super(session)
    }

    @Override
    void sendMessage(WebSocketMessage<?> message) throws IOException {
        logger.info("Sending message: {}", message.payload)
        super.sendMessage(message)
    }
}
