package uk.laxd.homeassistantclient.model.json.service

class ServiceBuilder {

    CallService callService

    private ServiceBuilder() {}

    static ServiceBuilder createServiceCall(String service) {
        def builder = new ServiceBuilder()
        builder.callService = new CallService(service)
        builder
    }

    ServiceBuilder forArea(String area) {
        callService.target.areas << area
        this
    }

    ServiceBuilder forDevice(String device) {
        callService.target.devices << device
        this
    }


    ServiceBuilder forEntity(String entity) {
        callService.target.entities << entity
        this
    }

    CallService build() {
        callService
    }

}
