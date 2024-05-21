package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.client.HomeAssistantClient

class Main {

    static void main(String[] args) {

        def client = HomeAssistantClient.createClient(args[0], args[1])

        client.getEntity("light.living_room_ceiling_1")
            .toggle()

        Thread.sleep(5000)

        client.turnOn("light.living_room_ceiling_1")

        Thread.sleep(100000)
    }


}
