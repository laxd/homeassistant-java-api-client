package uk.laxd.homeassistantclient.events

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.json.event.Event

@ToString
abstract class HomeAssistantEventListener<E extends Event> {
    int subscriptionId

    abstract void handleMessage(E event)

}