package uk.laxd.homeassistantclient.model.json.ws.outgoing

class PingWebSocketMessage extends SubscriptionWebSocketMessage {
    PingWebSocketMessage() {
        type = "ping"
    }
}
