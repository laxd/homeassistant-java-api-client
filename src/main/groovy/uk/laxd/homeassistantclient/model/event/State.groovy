package uk.laxd.homeassistantclient.model.event

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.LocalDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class State {

    @JsonProperty("entity_id")
    String entityId

    @JsonProperty("state")
    String state

    @JsonProperty("last_changed")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.[SSSSSS]XXX")
    LocalDateTime lastChanged

    @JsonProperty("last_updated")
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.[SSSSSS]XXX")
    LocalDateTime lastUpdated

    @JsonProperty("attributes")
    Map<String, Object> attributes


    @Override
    String toString() {
        "$entityId - $state"
    }
}
