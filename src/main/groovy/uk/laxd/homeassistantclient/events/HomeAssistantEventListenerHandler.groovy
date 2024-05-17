package uk.laxd.homeassistantclient.events


import uk.laxd.homeassistantclient.model.event.Event

interface HomeAssistantEventListenerHandler {
    void dispatchMessage(Event event)
}