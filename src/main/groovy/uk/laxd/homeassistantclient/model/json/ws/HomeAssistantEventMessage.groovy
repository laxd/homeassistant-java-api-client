package uk.laxd.homeassistantclient.model.json.ws

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.json.event.Event

class HomeAssistantEventMessage extends HomeAssistantResponseMessage {

    @JsonProperty("event")
    Event event

}
