package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        println(client.getEntity("light.bedroom"))

        client.ping()

        Thread.sleep(1000)

        client.onEvent("state_changed") {
            println("State changed! ${it.data}")
        }

        Thread.sleep(10000)
    }


}
