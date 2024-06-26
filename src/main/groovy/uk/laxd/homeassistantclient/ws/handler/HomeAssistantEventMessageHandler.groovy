package uk.laxd.homeassistantclient.ws.handler

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage

/**
 * Handles event messages and finds any listeners that are listening to the given subscription ID
 * and notifies them.
 */
@Named
@Slf4j
class HomeAssistantEventMessageHandler implements MessageHandler<ResponseWebSocketMessage> {

    private HomeAssistantEventListenerRegistry registry

    @Inject
    HomeAssistantEventMessageHandler(HomeAssistantEventListenerRegistry registry) {
        this.registry = registry
    }

    @Override
    void handle(WebSocketSession session, ResponseWebSocketMessage message) {
        registry.processMessage(message)
    }

    @Override
    boolean canHandle(IncomingWebSocketMessage message) {
        // Only messages with a subscription ID, i.e. messages sent in response to something
        // can be actioned by listeners
        return message instanceof ResponseWebSocketMessage
    }
}
