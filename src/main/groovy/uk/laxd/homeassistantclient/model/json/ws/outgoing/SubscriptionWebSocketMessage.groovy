package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonProperty

abstract class SubscriptionWebSocketMessage extends OutgoingWebSocketMessage {
    @JsonProperty("id")
    Integer subscriptionId
}
