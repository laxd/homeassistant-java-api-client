package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.laxd.homeassistantclient.model.json.event.jackson.StateChangedEventDeserialiser

import java.time.OffsetDateTime

@JsonDeserialize(using = StateChangedEventDeserialiser)
class StateChangedEvent extends Event {

    String origin
    OffsetDateTime timeFired
    State oldState
    State newState
    String entityId

}
