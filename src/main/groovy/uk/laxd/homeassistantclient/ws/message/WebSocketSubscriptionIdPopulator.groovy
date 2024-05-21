package uk.laxd.homeassistantclient.ws.message

import jakarta.inject.Inject
import jakarta.inject.Named
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.ws.IdGenerator
import uk.laxd.homeassistantclient.ws.message.model.SubscriptionWebSocketMessage

@Named
class WebSocketSubscriptionIdPopulator {

    private IdGenerator idGenerator

    @Inject
    WebSocketSubscriptionIdPopulator(IdGenerator idGenerator) {
        this.idGenerator = idGenerator
    }

    void linkMessageToListener(SubscriptionWebSocketMessage message, HomeAssistantEventListener eventListener) {
        def id = idGenerator.generateId()

        message.subscriptionId = id
        eventListener.subscriptionId = id
    }
}
