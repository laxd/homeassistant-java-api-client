package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.laxd.homeassistantclient.model.json.event.jackson.StateDeserialiser

import java.time.LocalDateTime

@JsonDeserialize(using = StateDeserialiser)
class StateChangedEvent extends Event {

    String origin

    @JsonProperty("time_fired")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.[SSSSSS]XXX")
    LocalDateTime timeFired

    State oldState
    State newState

    @JsonProperty("entity_id")
    String entityId
}
