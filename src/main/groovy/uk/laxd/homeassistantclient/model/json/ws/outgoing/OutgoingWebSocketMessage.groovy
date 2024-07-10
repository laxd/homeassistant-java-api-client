package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage

class OutgoingWebSocketMessage implements WebSocketMessage {

    @JsonProperty("type")
    String type

}
