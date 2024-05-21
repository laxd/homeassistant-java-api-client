package uk.laxd.homeassistantclient.ws.message

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage

@Component
class MessageHandlerDelegate {

    @Autowired
    private MessageHandlerRegistry registry

    void handle(WebSocketSession session, HomeAssistantWebSocketMessage message) {
        def handler = registry.handlers.stream()
                .filter { it.canHandle(message)}
                .findFirst()

        handler.ifPresent {
            it.handle(session, message)
        }
    }
}