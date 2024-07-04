package uk.laxd.homeassistantclient.model.domain.service

class Service {

    Service(ServiceType type) {
        this.type = type
    }

    ServiceType type
    Map<String, Object> serviceData = [:]
    Collection<ServiceTarget> serviceTargets = []


    @Override
    String toString() {
        return "$type - $serviceTargets"
    }
}

