package uk.laxd.homeassistantclient.model.domain

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

@ToString(includes = "entityId,state")
class Entity {

    private HomeAssistantWebSocketClient wsClient

    String entityId
    String state
    Date lastChanged
    Date lastUpdated
    Map<String, Object> attributes

    Entity(HomeAssistantWebSocketClient wsClient, HomeAssistantEntity entity) {
        this.wsClient = wsClient
        this.entityId = entity.entityId
        this.state = entity.state
        this.lastChanged = entity.lastChanged
        this.lastUpdated = entity.lastUpdated
        this.attributes = entity.attributes
    }

    void turnOn() {
        this.wsClient.turnOn(this.entityId)
    }

    void turnOff() {
        this.wsClient.turnOff(this.entityId)
    }

    void toggle() {
        this.wsClient.toggle(this.entityId)
    }

    String getType() {
        return entityId.split(".")[0]
    }

}
