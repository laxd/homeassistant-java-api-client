package uk.laxd.homeassistantclient.events

import jakarta.inject.Inject
import org.springframework.stereotype.Component
import uk.laxd.homeassistantclient.model.event.Event

@Component
class SpringHomeAssistantEventListenerHandler implements HomeAssistantEventListenerHandler {

    private HomeAssistantEventListenerRegistry registry

    @Inject
    SpringHomeAssistantEventListenerHandler(HomeAssistantEventListenerRegistry registry) {
        this.registry = registry
    }

    void dispatchMessage(Event event) {
        println("Handling event message...")
        registry.getRegisteredListeners().forEach {
            it.handleMessage(event)
        }
    }

}