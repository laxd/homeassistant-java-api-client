package uk.laxd.homeassistantclient.model.domain.service

import uk.laxd.homeassistantclient.model.json.service.JsonService

class ServiceBuilder {

    Service service

    private ServiceBuilder() {}

    static ServiceBuilder createServiceCall(ServiceType service) {
        def builder = new ServiceBuilder()
        builder.service = new Service(service)
        builder
    }

    ServiceBuilder forArea(String area) {
        callService.serviceTargets << new ServiceTarget(TargetType.AREA, area)
        this
    }

    ServiceBuilder forDevice(String device) {
        callService.serviceTargets << new ServiceTarget(TargetType.DEVICE, device)
        this
    }


    ServiceBuilder forEntity(String entity) {
        callService.serviceTargets << new ServiceTarget(TargetType.ENTITY, entity)
        this
    }

    JsonService build() {
        callService
    }

}
