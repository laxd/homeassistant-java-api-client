package uk.laxd.homeassistantclient.model.service

enum ServiceType {
    TURN_ON("homeassistant.turn_on"),
    TURN_OFF("homeassistant.turn_off"),
    TOGGLE("homeassistant.toggle")

    String text

    ServiceType(String text) {
        this.text = text
    }
}