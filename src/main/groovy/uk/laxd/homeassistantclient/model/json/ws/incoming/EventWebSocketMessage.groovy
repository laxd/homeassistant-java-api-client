package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.json.event.Event

class EventWebSocketMessage extends ResponseWebSocketMessage {

    @JsonProperty("event")
    Event event

    @Override
    String getType() {
        "event"
    }
}
