package uk.laxd.homeassistantclient.model.domain.service

import uk.laxd.homeassistantclient.model.json.service.JsonService

class ServiceBuilder {

    Service service

    private ServiceBuilder() {}

    static ServiceBuilder createServiceCall(String service) {
        def builder = new ServiceBuilder()
        builder.service = new Service(service)
        builder
    }

    ServiceBuilder forArea(String area) {
        service.targets << new ServiceTarget(TargetType.AREA, area)
        this
    }

    ServiceBuilder forDevice(String device) {
        service.targets << new ServiceTarget(TargetType.DEVICE, device)
        this
    }


    ServiceBuilder forEntity(String entity) {
        service.targets << new ServiceTarget(TargetType.ENTITY, entity)
        this
    }

    ServiceBuilder withAttribute(String key, Object value) {
        service.data[key] = value
        this
    }

    Service build() {
        service
    }

}
