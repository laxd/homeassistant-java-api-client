package uk.laxd.homeassistantclient.events

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.json.event.Event

/**
 * A listener for home assistant {@link Event}s.
 * @param <E>
 */
@ToString
abstract class HomeAssistantEventListener<E extends Event> implements HomeAssistantListener<E> {
    int subscriptionId

    abstract void handle(E event)

}