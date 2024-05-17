package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.annotation.JsonProperty

class HomeAssistantResponseMessage extends HomeAssistantWebSocketMessage {

    @JsonProperty("id")
    Integer subscriptionId

}
