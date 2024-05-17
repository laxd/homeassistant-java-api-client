package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.event.Event

class HomeAssistantEventMessage extends HomeAssistantResponseMessage {

    @JsonProperty("event")
    Event event

}
