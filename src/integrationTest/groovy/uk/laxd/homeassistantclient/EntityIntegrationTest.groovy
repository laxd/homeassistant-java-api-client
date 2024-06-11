package uk.laxd.homeassistantclient

import spock.lang.Specification
import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient
import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity

class EntityIntegrationTest extends Specification {

    String url = "http://localhost:8123"
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2YTliNmQwNTcyZjI0ZTZjODlhNmQwYjUxODA3MTU4NiIsImlhdCI6MTY5MjM1Mjk4NSwiZXhwIjoyMDA3NzEyOTg1fQ.sdnC7d7vjiPhWnV2xLQapwc91OuDcTQBDj6DnHapd1Y"

    HomeAssistantClient client

    void setup() {
        client = HomeAssistant.createClient()
            .connect(url, token)
    }

    def "Test entity state returned"() {
        when:
        def kitchenLight = client.getEntity("light.kitchen", LightEntity)
        kitchenLight.turnOff()
        Thread.sleep(10)

        then:
        client.getEntity("light.kitchen", LightEntity).state == 'off'

        when:
        kitchenLight.turnOn()
        Thread.sleep(10)

        then:
        client.getEntity("light.kitchen", LightEntity).state == 'on'
    }

}
