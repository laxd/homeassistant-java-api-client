package uk.laxd.homeassistantclient.ws.message.model

class PingWebSocketMessage extends SubscriptionWebSocketMessage {
    PingWebSocketMessage() {
        type = "ping"
    }
}
