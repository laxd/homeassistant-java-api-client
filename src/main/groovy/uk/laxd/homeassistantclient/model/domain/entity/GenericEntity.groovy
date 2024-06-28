package uk.laxd.homeassistantclient.model.domain.entity

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

import java.util.concurrent.TimeUnit

@ToString(includes = "entityId,state")
class GenericEntity implements Entity {

    protected HomeAssistantWebSocketClient wsClient

    String entityId
    String state
    Date lastChanged
    Date lastUpdated
    Map<String, Object> attributes

    GenericEntity(HomeAssistantWebSocketClient wsClient, HomeAssistantEntity entity) {
        this.wsClient = wsClient
        this.entityId = entity.entityId
        this.state = entity.state
        this.lastChanged = entity.lastChanged
        this.lastUpdated = entity.lastUpdated
        this.attributes = entity.attributes
    }

    @Override
    void callService(Service service) {
        this.wsClient.callService(service).get(10, TimeUnit.SECONDS)
    }

    @Override
    void turnOn() {
        this.wsClient.turnOn(this.entityId).get(10, TimeUnit.SECONDS)
    }

    @Override
    void turnOn(Map<String, Object> additionalData) {
        this.wsClient.turnOn(this.entityId, additionalData).get(10, TimeUnit.SECONDS)
    }

    @Override
    void turnOff() {
        this.wsClient.turnOff(this.entityId).get(10, TimeUnit.SECONDS)
    }

    @Override
    void turnOff(Map<String, Object> additionalData) {
        this.wsClient.turnOff(this.entityId, additionalData).get(10, TimeUnit.SECONDS)
    }

    @Override
    void toggle() {
        this.wsClient.toggle(this.entityId).get(10, TimeUnit.SECONDS)
    }

    @Override
    void toggle(Map<String, Object> additionalData) {
        this.wsClient.toggle(this.entityId, additionalData).get(10, TimeUnit.SECONDS)
    }

    String getType() {
        return entityId.split(".")[0]
    }

}
