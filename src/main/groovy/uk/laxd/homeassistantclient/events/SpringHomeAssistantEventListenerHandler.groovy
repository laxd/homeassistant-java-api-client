package uk.laxd.homeassistantclient.events

import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import uk.laxd.homeassistantclient.model.json.event.Event

@Named
class SpringHomeAssistantEventListenerHandler implements HomeAssistantEventListenerHandler {
    private static final Logger logger = LoggerFactory.getLogger(SpringHomeAssistantEventListenerHandler.class)

    private HomeAssistantEventListenerRegistry registry

    @Inject
    SpringHomeAssistantEventListenerHandler(HomeAssistantEventListenerRegistry registry) {
        this.registry = registry
    }

    void dispatchMessage(Event event) {
        logger.info("Handling event message '{}' - Passing to listeners", event)
        registry.getRegisteredListeners().forEach {
            it.handleMessage(event)
        }
    }

}