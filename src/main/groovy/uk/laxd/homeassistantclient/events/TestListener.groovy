package uk.laxd.homeassistantclient.events

import uk.laxd.homeassistantclient.model.event.Event

class TestListener extends HomeAssistantEventListener<Event> {
    void handleMessage(Event event) {
        println(event.data)
    }
}
