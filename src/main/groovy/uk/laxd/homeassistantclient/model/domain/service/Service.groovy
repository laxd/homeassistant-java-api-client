package uk.laxd.homeassistantclient.model.domain.service

class Service {

    Service(ServiceType type) {
        this.type = type
    }

    ServiceType type
    Map<String, Object> serviceData = [:]
    Collection<ServiceTarget> serviceTargets = []

}

enum ServiceType {
    TURN_ON("homeassistant", "turn_on"),
    TURN_OFF("homeassistant", "turn_off"),
    TOGGLE("homeassistant", "toggle")

    String domain
    String service

    ServiceType(String domain, String service) {
        this.domain = domain
        this.service = service
    }

}