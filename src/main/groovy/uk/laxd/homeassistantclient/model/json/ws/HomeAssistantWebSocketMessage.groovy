package uk.laxd.homeassistantclient.model.json.ws

import com.fasterxml.jackson.annotation.JsonAnySetter
import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonSubTypes.Type
import com.fasterxml.jackson.annotation.JsonTypeInfo

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        defaultImpl = HomeAssistantWebSocketMessage
)
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonSubTypes([
    @Type(value = HomeAssistantAuthRequiredMessage, name = "auth_required"),
    @Type(value = HomeAssistantAuthSuccessfulMessage, name = "auth_ok"),
    @Type(value = HomeAssistantAuthFailedMessage, name = "auth_invalid"),
    @Type(value = HomeAssistantEventMessage, name = "event"),
    @Type(value = HomeAssistantResponseMessage, name = "pong")
])
class HomeAssistantWebSocketMessage {

    Map<String, Object> unknown = [:]

    @JsonAnySetter
    void set(String name, Object value) {
        unknown.put(name, value);
    }

}