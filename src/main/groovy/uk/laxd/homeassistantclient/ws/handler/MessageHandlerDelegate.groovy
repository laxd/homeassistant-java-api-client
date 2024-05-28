package uk.laxd.homeassistantclient.ws.handler

import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage

@Named
class MessageHandlerDelegate {

    private List<MessageHandler> messageHandlers


    @Inject
    MessageHandlerDelegate(List<MessageHandler> messageHandlers) {
        this.messageHandlers = messageHandlers
    }

    void handle(WebSocketSession session, HomeAssistantWebSocketMessage message) {
        messageHandlers
                .findAll { it.canHandle(message)}
                .each {
                    it.handle(session, message)
                }
    }
}