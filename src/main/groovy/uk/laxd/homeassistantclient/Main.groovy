package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder

import java.time.Duration

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        client.getEntity("light.living_room_ceiling_1")
            .toggle()

        client.on(TriggerBuilder.onStateChange("light.living_room_ceiling_1").duration(Duration.ofSeconds(5)).build(), (e) -> {
            println("Changed ${e.data}")
        })

        Thread.sleep(5000)

        client.turnOn("light.living_room_ceiling_1")

        Thread.sleep(100000)
    }


}
