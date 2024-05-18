package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty

abstract class SubscriptionWebSocketMessage extends WebSocketMessage {
    @JsonProperty("id")
    Integer subscriptionId
}
