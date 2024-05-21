package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.trigger.builder.TriggerBuilder

import java.time.Duration
import java.util.concurrent.TimeUnit

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        println(client.getEntity("light.bedroom"))

        client.ping()

        def trigger = TriggerBuilder.onStateChange("light.bedroom", "light.kitchen")
                .from("off")
                .to("on")
                .duration(Duration.ofSeconds(5))
                .build()

        client.on(trigger, (triggerEvent) -> {
            println("Bedroom OR kitchen light turned on for 5 seconds!")
        })

        def timeTrigger = TriggerBuilder.dailyAt("15:00")
                .andAt("22:00:00")
                .build()

        client.on(timeTrigger, (triggerEvent) -> {
            // Something that needs to run at 15:00 or 22:00
            println("Triggered!")
        })

        def every10Seconds = TriggerBuilder.timePattern().every(10, TimeUnit.SECONDS)

        client.on(every10Seconds, (triggerEvent) -> {
            println("Every 10 seconds...")
        })

        def template = TriggerBuilder.valueTemplate("{{ is_state('light.bedroom', 'on') }}")
                .build()

        client.on([template, every10Seconds], (event) -> {
            println("Triggering EITHER every 10 seconds, OR when bedroom light turns on")
        })

        Thread.sleep(100000)
    }


}
