package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonSubTypes
import com.fasterxml.jackson.annotation.JsonTypeInfo
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthFailedMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthSuccessfulMessage

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        include = JsonTypeInfo.As.PROPERTY,
        property = "type",
        defaultImpl = UnknownWebSocketMessage
)
@JsonSubTypes([
        // Authentication messages
        @JsonSubTypes.Type(value = HomeAssistantAuthRequiredMessage, name = "auth_required"),
        @JsonSubTypes.Type(value = HomeAssistantAuthSuccessfulMessage, name = "auth_ok"),
        @JsonSubTypes.Type(value = HomeAssistantAuthFailedMessage, name = "auth_invalid"),

        // Other types of messages after authentication
        @JsonSubTypes.Type(value = EventResponseWebSocketMessage, name = "event"),
        @JsonSubTypes.Type(value = PongWebSocketMessage, name = "pong"),
        @JsonSubTypes.Type(value = ResultWebSocketMessage, name = "result"),
])
abstract class IncomingWebSocketMessage implements WebSocketMessage {

    /**
     * The type as indicated in the web socket message.
     */
    abstract String getType()

}

