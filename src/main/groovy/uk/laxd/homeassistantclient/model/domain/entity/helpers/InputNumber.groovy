package uk.laxd.homeassistantclient.model.domain.entity.helpers

import groovy.transform.InheritConstructors
import uk.laxd.homeassistantclient.model.domain.entity.AbstractEntity
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.TargetType

@InheritConstructors
class InputNumber extends AbstractEntity<Float> {

    public static final SET_VALUE_SERVICE = "input_number.set_value"
    public static final INCREMENT_SERVICE = "input_number.increment"
    public static final DECREMENT_SERVICE = "input_number.decrement"

    void setValue(int value) {
        def service = new Service(SET_VALUE_SERVICE)
        service.data = ["value": value]
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        this.wsClient.callService(service)
    }

    void increment() {
        def service = new Service(INCREMENT_SERVICE)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        this.wsClient.callService(service)
    }

    void decrement() {
        def service = new Service(DECREMENT_SERVICE)
        service.targets << new ServiceTarget(TargetType.ENTITY, entityId)
        this.wsClient.callService(service)
    }

}
