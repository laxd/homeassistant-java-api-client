package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonProperty

abstract class ResponseWebSocketMessage extends IncomingWebSocketMessage {

    @JsonProperty("id")
    Integer subscriptionId

}