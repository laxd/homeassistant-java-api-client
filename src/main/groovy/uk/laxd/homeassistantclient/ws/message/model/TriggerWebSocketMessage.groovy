package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.json.trigger.Trigger

class TriggerWebSocketMessage extends SubscriptionWebSocketMessage {

    TriggerWebSocketMessage(Trigger trigger) {
        this([trigger])
    }

    TriggerWebSocketMessage(Collection<Trigger> triggers) {
        this.type = "subscribe_trigger"
        this.triggers = triggers
    }
    @JsonProperty("trigger")
    Collection<Trigger> triggers

}


