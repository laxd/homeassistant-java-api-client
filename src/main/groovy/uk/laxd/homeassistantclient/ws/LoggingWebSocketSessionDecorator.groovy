package uk.laxd.homeassistantclient.ws

import groovy.util.logging.Slf4j
import org.springframework.web.socket.WebSocketMessage
import org.springframework.web.socket.WebSocketSession
import org.springframework.web.socket.handler.WebSocketSessionDecorator

@Slf4j
class LoggingWebSocketSessionDecorator extends WebSocketSessionDecorator {

    LoggingWebSocketSessionDecorator(WebSocketSession session) {
        super(session)
    }

    @Override
    void sendMessage(WebSocketMessage<?> message) throws IOException {
        log.debug("Sending message: {}", message.payload)
        super.sendMessage(message)
    }

}
