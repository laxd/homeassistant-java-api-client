package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder

import java.time.Duration

class Main {

    static void main(String[] args) {

        def client = HomeAssistant.createClient()
            .connect(args[0], args[1])

        def responseMessage = client.ping()

        println("Ping response: ${responseMessage.response}")

        def livingRoomCeiling1 = client.getEntity("light.living_room_ceiling_1")

        livingRoomCeiling1.toggle()

        client.on(TriggerBuilder.onStateChange("light.living_room_ceiling_1").duration(Duration.ofSeconds(5)).build(), (e) -> {
            // TODO: Figure out why this is an unknown event?
            println("Changed ${e}")
        })

        Thread.sleep(5000)

        client.toggle("light.living_room_ceiling_1")

        Thread.sleep(100000)
    }


}
