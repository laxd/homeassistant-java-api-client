package uk.laxd.homeassistantclient.model.json.ws.outgoing

import com.fasterxml.jackson.annotation.JsonUnwrapped
import uk.laxd.homeassistantclient.model.json.service.JsonService

class CallServiceWebSocketMessage extends SubscriptionWebSocketMessage {

    CallServiceWebSocketMessage(JsonService callService) {
        this.type = "call_service"
        this.callService = callService
    }

    @JsonUnwrapped
    JsonService callService

}


