package uk.laxd.homeassistantclient.ws.message

import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantEventMessage
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage

@Named
class EventMessageHandler implements MessageHandler<HomeAssistantEventMessage> {

    private static final Logger logger = LoggerFactory.getLogger(EventMessageHandler.class)
    @Inject
    private HomeAssistantEventListenerRegistry registry

    @Override
    void handle(WebSocketSession session, HomeAssistantEventMessage message) {
        registry.registeredListeners.stream().filter {
            it.subscriptionId == message.subscriptionId
        }.each {
            logger.info("Passing {} to listener {}", message, it)
            it.handleMessage(message.event)
        }
    }

    @Override
    boolean canHandle(HomeAssistantWebSocketMessage message) {
        return message instanceof HomeAssistantEventMessage
    }
}
