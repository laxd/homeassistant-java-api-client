package uk.laxd.homeassistantclient.model.domain.entity

import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.domain.entity.state.converter.StateConverter
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity
import uk.laxd.homeassistantclient.ws.HomeAssistantWebSocketClient

import java.util.concurrent.TimeUnit

@ToString(includes = "entityId,state")
class BaseEntity<S> implements Entity<S> {

    protected static final int TIMEOUT_SECONDS = 10

    protected HomeAssistantWebSocketClient wsClient

    String entityId
    S state
    Date lastChanged
    Date lastUpdated
    Map<String, Object> attributes

    BaseEntity(HomeAssistantWebSocketClient wsClient,
               HomeAssistantEntity entity,
               StateConverter<S> converter) {
        this.wsClient = wsClient
        this.entityId = entity.entityId
        this.lastChanged = entity.lastChanged
        this.lastUpdated = entity.lastUpdated
        this.attributes = entity.attributes

        this.state = converter.convertState(entity)
    }

    @Override
    void callService(Service service) {
        this.wsClient.callService(service).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void turnOn() {
        this.wsClient.turnOn(this.entityId).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void turnOn(Map<String, Object> additionalData) {
        this.wsClient.turnOn(this.entityId, additionalData).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void turnOff() {
        this.wsClient.turnOff(this.entityId).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void turnOff(Map<String, Object> additionalData) {
        this.wsClient.turnOff(this.entityId, additionalData).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void toggle() {
        this.wsClient.toggle(this.entityId).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    @Override
    void toggle(Map<String, Object> additionalData) {
        this.wsClient.toggle(this.entityId, additionalData).get(TIMEOUT_SECONDS, TimeUnit.SECONDS)
    }

    String getType() {
        entityId.split(".")[0]
    }

}
