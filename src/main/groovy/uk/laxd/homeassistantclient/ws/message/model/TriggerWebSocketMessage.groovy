package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger

class TriggerWebSocketMessage extends SubscriptionWebSocketMessage {

    TriggerWebSocketMessage(JsonTrigger trigger) {
        this([trigger])
    }

    TriggerWebSocketMessage(Collection<JsonTrigger> triggers) {
        this.type = "subscribe_trigger"
        this.triggers = triggers
    }

    @JsonProperty("trigger")
    Collection<JsonTrigger> triggers

}


