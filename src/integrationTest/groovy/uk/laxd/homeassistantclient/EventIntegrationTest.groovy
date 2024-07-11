package uk.laxd.homeassistantclient

import groovy.util.logging.Slf4j
import uk.laxd.homeassistantclient.model.json.event.Event

import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

@Slf4j
class EventIntegrationTest extends AbstractIntegrationTest {

    def "On event runs listener"() {
        given:
        def kitchenLight = client.getEntity("light.kitchen")

        def future = new CompletableFuture<>()

        when:
        client.onEvent("state_changed", (Event e) -> {
            log.info("Event fired: $e")
            future.complete("An entity has changed")
        })

        kitchenLight.turnOn()

        then:
        future.get(5, TimeUnit.SECONDS)
    }

}
