package uk.laxd.homeassistantclient.model.event

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.laxd.homeassistantclient.model.event.jackson.StateDeserialiser

@JsonDeserialize(using = StateDeserialiser)
class StateChangedEvent extends Event {

    State oldState
    State newState

    @JsonProperty("entity_id")
    String entityId
}
