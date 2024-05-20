package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.trigger.Trigger

class TriggerWebSocketMessage extends SubscriptionWebSocketMessage {

    TriggerWebSocketMessage(Trigger trigger) {
        this.type = "subscribe_trigger"
        this.trigger = trigger
    }

    @JsonProperty("trigger")
    Trigger trigger

}


