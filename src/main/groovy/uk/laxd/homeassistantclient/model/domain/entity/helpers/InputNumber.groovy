package uk.laxd.homeassistantclient.model.domain.entity.helpers

import groovy.transform.InheritConstructors
import uk.laxd.homeassistantclient.model.domain.entity.GenericEntity
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.ServiceType
import uk.laxd.homeassistantclient.model.domain.service.TargetType

@InheritConstructors
class InputNumber extends GenericEntity {

    void setValue(int value) {
        def service = new Service(ServiceType.INPUT_NUMBER_SET_VALUE)
        service.serviceData = ["value": value]
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        this.wsClient.callService(service)
    }

}
