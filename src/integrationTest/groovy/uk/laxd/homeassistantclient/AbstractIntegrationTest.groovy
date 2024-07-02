package uk.laxd.homeassistantclient

import groovy.transform.InheritConstructors
import groovy.util.logging.Slf4j
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.containers.wait.strategy.WaitStrategy
import org.testcontainers.containers.wait.strategy.WaitStrategyTarget
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification
import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

import java.time.Duration

@Slf4j
@Testcontainers
abstract class AbstractIntegrationTest extends Specification {

    String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiI2YTliNmQwNTcyZjI0ZTZjODlhNmQwYjUxODA3MTU4NiIsImlhdCI6MTY5MjM1Mjk4NSwiZXhwIjoyMDA3NzEyOTg1fQ.sdnC7d7vjiPhWnV2xLQapwc91OuDcTQBDj6DnHapd1Y"
    private HomeAssistantClient client

    def version = System.getProperty("HA_VERSION", "latest")

    GenericContainer container = new GenericContainer<>("homeassistant/home-assistant:$version")
            .withExposedPorts(8123)
            .withClasspathResourceMapping("test_config", "/config", BindMode.READ_WRITE)
            .waitingFor(Wait.forLogMessage(".*\\[homeassistant\\.core\\] Starting Home Assistant.*", 1))

    String getClientUrl() {
        "http://${container.host}:${container.firstMappedPort}"
    }

    HomeAssistantClient getClient() {
        if (!this.client) {
            String url = getClientUrl()

            log.info("Creating new HA client - $url")

            this.client = HomeAssistant.createClient().connect(url, token)
        }

        this.client
    }

    void waitUntil(Closure condition, retry=5) {
        for (i in 0..retry) {
            if (condition()) {
                return
            }
            Thread.sleep(10)
        }

        throw new MaxRetriesExceededException("Retried condition $retry times but was never successful")
    }

}

@InheritConstructors
class MaxRetriesExceededException extends RuntimeException {

}