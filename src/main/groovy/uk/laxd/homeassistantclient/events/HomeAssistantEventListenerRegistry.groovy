package uk.laxd.homeassistantclient.events

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage

@Component
class HomeAssistantEventListenerRegistry {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantEventListenerRegistry.class)

    Map<Integer, HomeAssistantListener> conversationIdToListenerMap = [:]

    void register(HomeAssistantListener listener, Integer conversationId) {
        conversationIdToListenerMap.put(conversationId, listener)
        logger.debug("Added listener {}", listener)
    }

    void unregister(HomeAssistantListener listener) {
        conversationIdToListenerMap.removeAll {it == listener }
        logger.debug("Removed listener {}", listener)
    }

    void processMessage(ResponseWebSocketMessage message) {
        def listener = conversationIdToListenerMap[message.subscriptionId]

        if (listener) {
            listener.handle(message)
        }
        else {
            logger.warn("Received ResponseWebSocketMessage with subscription ID ${message.subscriptionId}, but no listener was registered with this ID")
        }
    }

}