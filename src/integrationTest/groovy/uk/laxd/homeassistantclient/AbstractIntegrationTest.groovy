package uk.laxd.homeassistantclient

import spock.lang.Specification
import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

import java.util.concurrent.TimeoutException

abstract class AbstractIntegrationTest extends Specification {

    private HomeAssistantClient client

    HomeAssistantClient getClient() {
        if (!this.client) {
            String url = "http://localhost:8123"
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2YTliNmQwNTcyZjI0ZTZjODlhNmQwYjUxODA3MTU4NiIsImlhdCI6MTY5MjM1Mjk4NSwiZXhwIjoyMDA3NzEyOTg1fQ.sdnC7d7vjiPhWnV2xLQapwc91OuDcTQBDj6DnHapd1Y"

            this.client = HomeAssistant.createClient().connect(url, token)
        }

        this.client
    }

    void waitUntil(Closure condition, retry=5) {
        retry.times {
            if (condition()) {
                return
            }
            Thread.sleep(10)
        }

        throw new TimeoutException()
    }

}
