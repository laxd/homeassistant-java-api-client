package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class EntityIntegrationTest extends AbstractIntegrationTest {

    def "Test entity state returned"() {
        given:
        def client = getClient()

        when:
        def kitchenLight = client.getEntity("light.kitchen", LightEntity)
        kitchenLight.turnOff()

        then:
        waitUntil {
            client.getEntity("light.kitchen", LightEntity).state == 'off'
        }

        when:
        kitchenLight.turnOn()

        then:
        waitUntil {
            client.getEntity("light.kitchen", LightEntity).state == 'on'
        }
    }

    def "On state change trigger runs listener"() {
        given:
        def client = getClient()
        def kitchenLight = client.getEntity("light.kitchen")

        def future = new CompletableFuture<>()

        when:
        client.on(TriggerBuilder.onStateChange("light.kitchen").duration(Duration.ofSeconds(1)).build(), (TriggerEvent e) -> {
            future.complete("Kitchen light triggered")
        })

        kitchenLight.turnOn()

        then:
        future.get(5, TimeUnit.SECONDS)
    }
}
