package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.annotation.JsonUnwrapped
import uk.laxd.homeassistantclient.model.json.service.CallService

class CallServiceWebSocketMessage extends SubscriptionWebSocketMessage {

    CallServiceWebSocketMessage(CallService callService) {
        this.type = "call_service"
        this.callService = callService
    }

    @JsonUnwrapped
    CallService callService

}


