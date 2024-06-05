package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.model.domain.entity.light.Colour
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder

import java.time.Duration

class Main {

    static void main(String[] args) {
        def url = args[0]
        def token = args[1]

        // Create a new instance of the client and connect using the URL of your Home Assistant instance
        // and a long lived access token.
        def client = HomeAssistant.createClient()
            .connect(url, token)

        // We can ping the server to ensure it's up
        def responseMessage = client.ping()
        println("Ping response: ${responseMessage.response}")

        // Entities can be retrieved using their entity ID, and are returned as a specific type of that entity
        def livingRoomCeiling1 = client.getEntity("light.living_room_ceiling_1", LightEntity)

        // Some methods are available to all types of entities, e.g. turn on, turn off and toggle
        livingRoomCeiling1.turnOn()

        // Some are available only on certain types, e.g. lights have brightness
        println("Living room light at ${livingRoomCeiling1.brightnessPercent}% brightness")

        // And entities can be changed using the convenience methods too
        livingRoomCeiling1.setBrightness(200.shortValue())
        livingRoomCeiling1.setColour(Colour.RED)
        livingRoomCeiling1.setColourTemperature(300 as short)

        // You can react to changes by listening for events or triggers using the HomeAssistantClient object
        client.on(TriggerBuilder.onStateChange("light.living_room_ceiling_1").duration(Duration.ofSeconds(5)).build(), (e) -> {
            println("${e.entityId} Changed: ${e}")
        })

        Thread.sleep(5000)

        Thread.sleep(100000)
    }


}
