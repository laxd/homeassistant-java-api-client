package uk.laxd.homeassistantclient.model

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.Date

class Entity {

    @JsonProperty("entity_id")
    String entityId
    String state

    @JsonProperty("last_changed")
    Date lastChanged

    @JsonProperty("last_updated")
    Date lastUpdated

    Map<String, Object> attributes

    String getType() {
        return entityId.split(".")[0]
    }

    @Override
    String toString() {
        return "$entityId - $attributes"
    }
}