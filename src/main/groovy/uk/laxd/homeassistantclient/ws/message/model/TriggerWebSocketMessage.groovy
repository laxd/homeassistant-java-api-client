package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty

class TriggerWebSocketMessage extends SubscriptionWebSocketMessage {

    TriggerWebSocketMessage(String platform, String entityId, String from, String to) {
        this.type = "subscribe_trigger"
        this.trigger = new Trigger()
        this.trigger.platform = platform
        this.trigger.entityId = entityId
        this.trigger.stateFrom = from
        this.trigger.stateTo = to
    }

    @JsonProperty("trigger")
    Trigger trigger

    static class Trigger {
        @JsonProperty("platform")
        String platform

        @JsonProperty("entity_id")
        String entityId

        @JsonProperty("from")
        String stateFrom

        @JsonProperty("to")
        String stateTo
    }

}


