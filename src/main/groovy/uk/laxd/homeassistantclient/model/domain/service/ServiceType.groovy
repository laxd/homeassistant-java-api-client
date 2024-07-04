package uk.laxd.homeassistantclient.model.domain.service

enum ServiceType {
    TURN_ON("homeassistant", "turn_on"),
    TURN_OFF("homeassistant", "turn_off"),
    TOGGLE("homeassistant", "toggle"),
    INPUT_NUMBER_SET_VALUE("input_number", "set_value")

    String domain
    String service

    ServiceType(String domain, String service) {
        this.domain = domain
        this.service = service
    }


    @Override
    String toString() {
        return "$domain.$service"
    }
}