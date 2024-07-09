package uk.laxd.homeassistantclient.model.json

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonTypeInfo
import java.util.Date

/**
 * A JSON representation of a home assistant entity, as contained within a {@code WebSocketMessage} received.
 */
class HomeAssistantEntity {

    @JsonProperty("entity_id")
    String entityId
    String state

    @JsonProperty("last_changed")
    Date lastChanged

    @JsonProperty("last_updated")
    Date lastUpdated

    Map<String, Object> attributes

    String getDomain() {
        if (entityId == null) {
            null
        }

        def parts = entityId.split("\\.")

        parts.length > 1 ? parts[0] : null
    }

    @Override
    String toString() {
        return "$entityId - $attributes"
    }
}