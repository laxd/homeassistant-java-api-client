package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.TupleConstructor

@TupleConstructor
class AuthenticationWebSocketMessage extends WebSocketMessage {

    @JsonProperty("access_token")
    String token

    @Override
    String getType() {
        return "auth"
    }
}
