package uk.laxd.homeassistantclient.events

import groovy.util.logging.Slf4j
import org.springframework.stereotype.Component
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventResponseWebSocketMessage

/**
 * Registers listeners to perform actions when a message arrives from the Home Assistant server with
 * a matching subscription ID.
 */
@Component
@Slf4j
class HomeAssistantEventListenerRegistry {

    Map<Integer, HomeAssistantListener> conversationIdToListenerMap = [:]

    void register(HomeAssistantListener listener, Integer conversationId) {
        conversationIdToListenerMap.put(conversationId, listener)
        log.debug("Added listener $listener")
    }

    void unregister(HomeAssistantListener listener) {
        conversationIdToListenerMap.removeAll { it == listener }
        log.debug("Removed listener $listener")

        // TODO: Remove the listener from HA too.
    }

    void processMessage(EventResponseWebSocketMessage message) {
        if (message || message.event) {
            log.error("Cannot process null EventWebSocketMessage or message containing a null Event.")
        }

        def listener = conversationIdToListenerMap[message.subscriptionId]

        if (listener) {
            // TODO: The types should match, but might need to do some error handling here
            listener.handle(message.event)
        }
        else {
            log.warn("Received ResponseWebSocketMessage with subscription ID ${message.subscriptionId}, " +
                    "but no listener was registered with this ID")
        }
    }

}
