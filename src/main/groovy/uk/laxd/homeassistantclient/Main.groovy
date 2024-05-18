package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        println(client.getEntity("light.bedroom"))

        client.ping()

        Thread.sleep(1000)

        client.onStateChanged("light.bedroom", "off", "on", (stateChangedEvent) -> {
            println("Bedroom light turned on!")
        })

        Thread.sleep(100000)
    }


}
