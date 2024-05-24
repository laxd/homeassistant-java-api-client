package uk.laxd.homeassistantclient.model.domain.entity

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

@ToString(includes = "entityId,state")
class EntityImpl implements Entity {

    private HomeAssistantWebSocketClient wsClient

    String entityId
    String state
    Date lastChanged
    Date lastUpdated
    Map<String, Object> attributes

    EntityImpl(HomeAssistantWebSocketClient wsClient, HomeAssistantEntity entity) {
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

    void turnOn(Map<String, Object> additionalData) {
        this.wsClient.turnOn(this.entityId, additionalData)
    }

    void turnOff() {
        this.wsClient.turnOff(this.entityId)
    }

    void turnOff(Map<String, Object> additionalData) {
        this.wsClient.turnOff(this.entityId, additionalData)
    }

    void toggle() {
        this.wsClient.toggle(this.entityId)
    }

    void toggle(Map<String, Object> additionalData) {
        this.wsClient.toggle(this.entityId, additionalData)
    }

    String getType() {
        return entityId.split(".")[0]
    }

}
