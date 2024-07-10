package uk.laxd.homeassistantclient.examples

import uk.laxd.homeassistantclient.model.domain.service.ServiceBuilder

class ServiceInteraction extends BaseExample {

    static void main(String[] args) {
        def client = newClient()

        def entityId = "light.bedroom"

        // Some services are pre-defined e.g.
        client.turnOn(entityId)

        // For other types, you can callService to call that service
        def turnLightOn = ServiceBuilder.createServiceCall("light.turn_on")
                .forEntity(entityId)
                .withAttribute("brightness", 255)
                .build()

        client.callService(turnLightOn)
    }

}
