package uk.laxd.homeassistantclient

import groovy.util.logging.Slf4j
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification
import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

import java.util.concurrent.TimeoutException

@Slf4j
@Testcontainers
abstract class AbstractIntegrationTest extends Specification {

    private HomeAssistantClient client

    def version = System.getProperty("HA_VERSION", "latest")

    GenericContainer container = new GenericContainer<>("homeassistant/home-assistant:$version")
            .withExposedPorts(8123)
            .withClasspathResourceMapping("test_config", "/config", BindMode.READ_WRITE)

    HomeAssistantClient getClient() {
        if (!this.client) {
            String url = "http://${container.host}:${container.firstMappedPort}"
            String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2YTliNmQwNTcyZjI0ZTZjODlhNmQwYjUxODA3MTU4NiIsImlhdCI6MTY5MjM1Mjk4NSwiZXhwIjoyMDA3NzEyOTg1fQ.sdnC7d7vjiPhWnV2xLQapwc91OuDcTQBDj6DnHapd1Y"

            log.info("Creating new HA client - $url")

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
