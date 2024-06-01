package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.laxd.homeassistantclient.model.json.event.jackson.StateDeserialiser

import java.time.OffsetDateTime

@JsonDeserialize(using = StateDeserialiser)
class StateChangedEvent extends Event {
    String origin
    OffsetDateTime timeFired
    State oldState
    State newState
    String entityId
}
