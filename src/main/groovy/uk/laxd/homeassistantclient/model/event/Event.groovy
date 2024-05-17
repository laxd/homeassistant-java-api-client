package uk.laxd.homeassistantclient.model.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo

import java.time.LocalDateTime

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.EXTERNAL_PROPERTY,
        property = "event_type",
        defaultImpl = UnknownEvent
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes([
        @JsonSubTypes.Type(value = StateChangedEvent, name = "state_changed"),
])
class Event {

    @JsonProperty("data")
    Map<String, Object> data

    String origin

    @JsonProperty("time_fired")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.[SSSSSS]XXX")
    LocalDateTime timeFired

    @JsonProperty("context")
    Map<String, Object> context
}