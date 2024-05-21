package uk.laxd.homeassistantclient.events


import uk.laxd.homeassistantclient.model.json.event.Event

interface HomeAssistantEventListenerHandler {
    void dispatchMessage(Event event)
}