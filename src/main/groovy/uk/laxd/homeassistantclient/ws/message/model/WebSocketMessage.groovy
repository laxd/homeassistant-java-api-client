package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty

abstract class WebSocketMessage {

    @JsonProperty("type")
    String type

}
