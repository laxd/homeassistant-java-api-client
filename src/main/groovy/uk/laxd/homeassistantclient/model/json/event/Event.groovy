package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

import java.time.LocalDateTime

@JsonTypeInfo(
        use = JsonTypeInfo.Id.DEDUCTION
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes([
    @JsonSubTypes.Type(StateChangedEvent),
    @JsonSubTypes.Type(TriggerEvent)
])
class Event {

    @JsonProperty("context")
    Map<String, Object> context
}