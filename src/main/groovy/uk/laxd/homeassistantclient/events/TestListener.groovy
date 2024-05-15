package uk.laxd.homeassistantclient.events

import uk.laxd.homeassistantclient.model.Event

class TestListener extends HomeAssistantEventListener {
    void handleMessage(Event event) {
        println(event.data)
    }
}
