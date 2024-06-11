package uk.laxd.homeassistantclient

import spock.lang.Specification
import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

class SmokeIntegrationTest extends Specification {

    String url = "http://localhost:8123"
    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2YTliNmQwNTcyZjI0ZTZjODlhNmQwYjUxODA3MTU4NiIsImlhdCI6MTY5MjM1Mjk4NSwiZXhwIjoyMDA3NzEyOTg1fQ.sdnC7d7vjiPhWnV2xLQapwc91OuDcTQBDj6DnHapd1Y"

    HomeAssistantClient client

    void setup() {
        client = HomeAssistant.createClient()
            .connect(url, token)
    }

    def "Basic ping test smoke test"() {
        when:
        def pong = client.ping()

        then:
        pong.response == "pong"
    }

}
