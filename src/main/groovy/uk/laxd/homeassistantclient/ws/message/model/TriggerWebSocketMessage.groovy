package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

import java.time.Duration

class TriggerWebSocketMessage extends SubscriptionWebSocketMessage {

    TriggerWebSocketMessage(uk.laxd.homeassistantclient.model.trigger.Trigger trigger) {
        this.type = "subscribe_trigger"
        this.trigger = new Trigger()
        this.trigger.platform = trigger.triggerType.string
        this.trigger.entityId = trigger.entity
        this.trigger.stateFrom = trigger.from
        this.trigger.stateTo = trigger.to
        this.trigger.duration = trigger.duration
    }

    @JsonProperty("trigger")
    Trigger trigger

    static class Trigger {
        @JsonProperty("platform")
        String platform

        @JsonProperty("entity_id")
        String entityId

        @JsonProperty("from")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String stateFrom

        @JsonProperty("to")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        String stateTo

        @JsonProperty("for")
        @JsonInclude(JsonInclude.Include.NON_EMPTY)
        Duration duration
    }

}


