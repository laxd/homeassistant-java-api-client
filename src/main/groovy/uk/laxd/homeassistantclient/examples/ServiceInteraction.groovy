package uk.laxd.homeassistantclient.examples

import uk.laxd.homeassistantclient.model.domain.service.ServiceBuilder

class ServiceInteraction extends AbstractExample {

    static void main(String[] args) {
        def client = createClient()

        // Some services are pre-defined e.g.
        client.turnOn("light.bedroom")

        // For other types, you can callService to call that service
        def turnLightOn = ServiceBuilder.createServiceCall("light.turn_on")
                .forEntity("light.bedroom")
                .withAttribute("brightness", 255)
                .build()

        client.callService(turnLightOn)
    }


}
