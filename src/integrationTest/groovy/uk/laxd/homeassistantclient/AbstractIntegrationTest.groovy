package uk.laxd.homeassistantclient

import groovy.transform.InheritConstructors
import groovy.util.logging.Slf4j
import org.testcontainers.containers.BindMode
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification
import uk.laxd.homeassistantclient.client.HomeAssistant
import uk.laxd.homeassistantclient.client.HomeAssistantClient

import java.nio.file.Files
import java.nio.file.Paths

@Slf4j
@Testcontainers
abstract class AbstractIntegrationTest extends Specification {

    String token = Files.readString(Paths.get(ClassLoader.getSystemResource("homeAssistantToken").toURI()))
    private HomeAssistantClient client

    String version = System.getProperty("HA_VERSION", "latest")

    GenericContainer container = new GenericContainer<>("homeassistant/home-assistant:$version")
            .withExposedPorts(8123)
            .withClasspathResourceMapping("test_config", "/config", BindMode.READ_WRITE)
            .waitingFor(Wait.forLogMessage(".*\\[homeassistant\\.core\\] Starting Home Assistant.*", 1))

    String clientUrl() {
        "http://${container.host}:${container.firstMappedPort}"
    }

    HomeAssistantClient getClient() {
        if (!this.client) {
            String url = clientUrl()

            log.info("Creating new HA client - $url")

            this.client = HomeAssistant.createClient().connect(url, token)
        }

        this.client
    }

    void waitUntil(Closure condition, int retry=5) {
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
