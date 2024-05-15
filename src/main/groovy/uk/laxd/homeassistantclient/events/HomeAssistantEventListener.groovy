package uk.laxd.homeassistantclient.events

import uk.laxd.homeassistantclient.model.Event
import kotlin.properties.Delegates

abstract class HomeAssistantEventListener {
    int subscriptionId

    abstract void handleMessage(Event event)
}