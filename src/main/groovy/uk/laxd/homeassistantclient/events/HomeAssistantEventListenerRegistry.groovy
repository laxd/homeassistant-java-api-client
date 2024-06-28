package uk.laxd.homeassistantclient.events

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventWebSocketMessage

/**
 * Registers listeners to perform actions when a message arrives from the Home Assistant server with
 * a matching subscription ID.
 */
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

        // TODO: Remove the listener from HA too.
    }

    void processMessage(EventWebSocketMessage message) {
        if (message || message.event) {
            logger.error("Cannot process null EventWebSocketMessage or message containing a null Event.")
        }

        def listener = conversationIdToListenerMap[message.subscriptionId]

        if (listener) {
            // TODO: The types should match, but might need to do some error handling here
            listener.handle(message.event)
        }
        else {
            logger.warn("Received ResponseWebSocketMessage with subscription ID ${message.subscriptionId}, but no listener was registered with this ID")
        }
    }

}