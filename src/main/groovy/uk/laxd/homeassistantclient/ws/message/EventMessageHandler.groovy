package uk.laxd.homeassistantclient.ws.message

import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.ws.HomeAssistantEventMessage
import uk.laxd.homeassistantclient.model.ws.HomeAssistantWebSocketMessage

@Named
class EventMessageHandler implements MessageHandler<HomeAssistantEventMessage> {

    @Inject
    private HomeAssistantEventListenerRegistry registry

    @Override
    void handle(WebSocketSession session, HomeAssistantEventMessage message) {
        registry.registeredListeners.each {
            // TODO: Filter based on ID of the message
            // Filter based on message subscription
            println("Handling message")
            it.handleMessage(message.event)
        }
    }

    @Override
    boolean canHandle(HomeAssistantWebSocketMessage message) {
        return message instanceof HomeAssistantEventMessage
    }
}
