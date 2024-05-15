package uk.laxd.homeassistantclient.events

import org.springframework.stereotype.Component

@Component
class HomeAssistantEventListenerRegistry {

    List<HomeAssistantEventListener> listeners = []

    void register(HomeAssistantEventListener listener) {
        listeners.add(listener)
    }

    void unregister(HomeAssistantEventListener listener) {
        listeners.remove(listener)
    }

    List<HomeAssistantEventListener> getRegisteredListeners() {
        return listeners
    }

}