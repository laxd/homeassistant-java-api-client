package uk.laxd.homeassistantclient.model.json.event


import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.OffsetDateTime

@JsonIgnoreProperties(ignoreUnknown = true)
class State {

    @JsonProperty("entity_id")
    String entityId

    @JsonProperty("state")
    String state

    @JsonProperty("last_changed")
    OffsetDateTime lastChanged

    @JsonProperty("last_updated")
    OffsetDateTime lastUpdated

    @JsonProperty("attributes")
    Map<String, Object> attributes

    @Override
    String toString() {
        "$entityId - $state"
    }
}
