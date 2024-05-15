package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        defaultImpl = HomeAssistantWebSocketMessage.class,
        visible = true
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes([
    @Type(value = HomeAssistantAuthRequiredMessage.class, name = "auth_required"),
    @Type(value = HomeAssistantEventMessage, name = "event")
])
class HomeAssistantWebSocketMessage {

    private Map<String, Object> unknown = [:]

    @JsonAnySetter
    void set(String name, Object value) {
        unknown.put(name, value);
    }

}