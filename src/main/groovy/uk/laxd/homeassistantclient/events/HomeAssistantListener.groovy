package uk.laxd.homeassistantclient.events

import uk.laxd.homeassistantclient.model.json.event.Event

/**
 * A listener that will be notified when event messages are received from the Home Assistant server.
 * @param <O> Type of object to handle
 */
interface HomeAssistantListener<E extends Event> {

    abstract void handle(E object)

}
