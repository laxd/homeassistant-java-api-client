package uk.laxd.homeassistantclient.events

import jakarta.inject.Inject
import org.springframework.stereotype.Component
import uk.laxd.homeassistantclient.model.Event

interface HomeAssistantEventListenerHandler {
    void dispatchMessage(Event event)
}