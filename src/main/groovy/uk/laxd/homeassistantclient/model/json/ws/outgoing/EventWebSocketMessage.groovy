package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonProperty

class EventWebSocketMessage extends SubscriptionWebSocketMessage {

    EventWebSocketMessage(String eventType) {
        this([eventType])
    }

    EventWebSocketMessage(Collection<String> eventTypes) {
        this.type = "subscribe_events"
        this.eventTypes = eventTypes
    }

    @JsonProperty("event_type")
    Collection<String> eventTypes

}


