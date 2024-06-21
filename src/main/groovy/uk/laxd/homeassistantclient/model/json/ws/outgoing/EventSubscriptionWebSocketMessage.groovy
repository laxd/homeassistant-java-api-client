package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonProperty

class EventSubscriptionWebSocketMessage extends SubscriptionWebSocketMessage {

    EventSubscriptionWebSocketMessage(String eventType) {
        this([eventType])
    }

    EventSubscriptionWebSocketMessage(Collection<String> eventTypes) {
        this.type = "subscribe_events"
        this.eventTypes = eventTypes
    }

    @JsonProperty("event_type")
    Collection<String> eventTypes

}


