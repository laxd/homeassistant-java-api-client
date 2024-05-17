package uk.laxd.homeassistantclient.events

import uk.laxd.homeassistantclient.model.event.Event

abstract class HomeAssistantEventListener<E extends Event> {
    int subscriptionId

    abstract void handleMessage(E event)


    @Override
    String toString() {
        "HomeAssistantEventListener{" +
            "subscriptionId=" + subscriptionId +
            '}'
    }
}