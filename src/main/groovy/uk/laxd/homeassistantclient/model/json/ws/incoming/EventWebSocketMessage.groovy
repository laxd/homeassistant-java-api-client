package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.json.event.Event

/**
 * A message in response to a {@link uk.laxd.homeassistantclient.model.json.ws.outgoing.EventSubscriptionWebSocketMessage}
 * that we sent previously.
 */
// TODO: Rename EventResponseWebSocketMessage
class EventWebSocketMessage extends ResponseWebSocketMessage {

    @JsonProperty("event")
    Event event

    @Override
    String getType() {
        "event"
    }
}
