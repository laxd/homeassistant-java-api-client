package uk.laxd.homeassistantclient.events

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventWebSocketMessage
import uk.laxd.homeassistantclient.ws.WebSocketListener

/**
 * A listener for home assistant {@link Event}s. Instances of this class will ONLY receive messages that match
 * the {@see subscriptionId} provided.
 *
 * For messages that do match the given subscription ID, the given {@link Event} is extracted from the message
 * and delegated to the handleEvent method.
 * @param <E>
 */
@ToString
abstract class HomeAssistantEventListener<E extends Event> extends WebSocketListener<EventWebSocketMessage> {
    int subscriptionId

    abstract void handleEvent(E event)

    @Override
    void handle(EventWebSocketMessage message) {
        // TODO: Check if the event is the correct type? Reject/ignore/throw exception if it doesn't match?
        handleEvent(message.event as E)
    }

}