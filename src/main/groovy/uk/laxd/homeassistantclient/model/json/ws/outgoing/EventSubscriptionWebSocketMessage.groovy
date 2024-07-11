package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonProperty

class EventSubscriptionWebSocketMessage extends SubscriptionWebSocketMessage {

    final String type = "subscribe_events"

    EventSubscriptionWebSocketMessage(String eventType) {
        this.eventType = eventType
    }

    @JsonProperty("event_type")
    String eventType

}
