package uk.laxd.homeassistantclient.model.domain.service

class Service {

    Service(String type) {
        this.type = type
    }

    String type
    Map<String, Object> data = [:]
    Collection<ServiceTarget> targets = []

    @Override
    String toString() {
        return "$type - $targets"
    }
}

