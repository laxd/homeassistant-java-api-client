package uk.laxd.homeassistantclient.model.domain.entity

import groovy.transform.ToString
import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.domain.entity.state.converter.StateConverter
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.json.HomeAssistantEntity

@ToString(includes = "entityId,state")
class BaseEntity<S> implements Entity<S> {

    protected HomeAssistantClient client

    String entityId
    S state
    Date lastChanged
    Date lastUpdated
    Map<String, Object> attributes

    BaseEntity(HomeAssistantClient client,
               HomeAssistantEntity entity,
               StateConverter<S> converter) {
        this.client = client

        this.entityId = entity.entityId
        this.lastChanged = entity.lastChanged
        this.lastUpdated = entity.lastUpdated
        this.attributes = entity.attributes

        this.state = converter.convertState(entity)
    }

    @Override
    void callService(Service service) {
        this.client.callService(service)
    }

    @Override
    void turnOn() {
        this.client.turnOn(this.entityId)
    }

    @Override
    void turnOn(Map<String, Object> additionalData) {
        this.client.turnOn(this.entityId, additionalData)
    }

    @Override
    void turnOff() {
        this.client.turnOff(this.entityId)
    }

    @Override
    void turnOff(Map<String, Object> additionalData) {
        this.client.turnOff(this.entityId, additionalData)
    }

    @Override
    void toggle() {
        this.client.toggle(this.entityId)
    }

    @Override
    void toggle(Map<String, Object> additionalData) {
        this.client.toggle(this.entityId, additionalData)
    }

    String getType() {
        entityId.split(".")[0]
    }

}
