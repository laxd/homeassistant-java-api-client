package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        println(client.getEntity("light.bedroom"))

        client.ping()

        client.turnOn("light.living_room")

        Thread.sleep(100000)
    }


}
