package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.trigger.TriggerBuilder

import java.time.Duration

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        println(client.getEntity("light.bedroom"))

        client.ping()

        def trigger = TriggerBuilder.onStateChange("light.bedroom")
                .from("off")
                .to("on")
                .duration(Duration.ofSeconds(5))
                .build()

        client.onStateChanged(trigger, (stateChangedEvent) -> {
            println("Bedroom light turned on for 5 seconds!")
        })

        def trigger2 = TriggerBuilder.onStateChange("light.living_room")
                .to("on")
                .build()

        client.onStateChanged(trigger2, (stateChangedEvent) -> {
            // This will only be run when the light changes to "on",
            // regardless of what the state was before
            println("Living room light turned on")
        })

        Thread.sleep(100000)
    }


}
