package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.Event

class HomeAssistantEventMessage extends HomeAssistantWebSocketMessage {

    @JsonProperty("event")
    public
    Event event

}
