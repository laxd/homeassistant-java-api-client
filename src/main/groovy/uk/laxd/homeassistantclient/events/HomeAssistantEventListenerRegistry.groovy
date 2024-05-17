package uk.laxd.homeassistantclient.events

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class HomeAssistantEventListenerRegistry {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantEventListenerRegistry.class)

    List<HomeAssistantEventListener> listeners = []

    void register(HomeAssistantEventListener listener) {
        listeners.add(listener)
        logger.debug("Added listener {}", listener.subscriptionId)
    }

    void unregister(HomeAssistantEventListener listener) {
        listeners.remove(listener)
        logger.debug("Removed listener {}", listener.subscriptionId)
    }

    List<HomeAssistantEventListener> getRegisteredListeners() {
        return listeners
    }

}