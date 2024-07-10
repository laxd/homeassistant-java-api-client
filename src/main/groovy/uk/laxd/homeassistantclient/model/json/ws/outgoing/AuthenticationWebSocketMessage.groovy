package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonProperty
import groovy.transform.TupleConstructor

@TupleConstructor
class AuthenticationWebSocketMessage extends OutgoingWebSocketMessage {

    @JsonProperty("access_token")
    String token

    final String type = "auth"

}
