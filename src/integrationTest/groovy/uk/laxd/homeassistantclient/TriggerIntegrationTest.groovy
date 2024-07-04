package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.model.domain.entity.helpers.InputNumber
import uk.laxd.homeassistantclient.model.domain.trigger.builder.TriggerBuilder
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

import java.time.Duration
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class TriggerIntegrationTest extends AbstractIntegrationTest {
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

    def "On numeric state change trigger runs listener"() {
        given:
        def client = getClient()
        def inputNumber = client.getEntity("input_number.test_number_1") as InputNumber
        inputNumber.setValue(10)

        def future = new CompletableFuture<>()

        when:
        client.on(TriggerBuilder.onNumericStateChange("input_number.test_number_1").below("5").build(), (TriggerEvent e) -> {
            future.complete("Input number changed")
        })

        inputNumber.setValue(2)

        then:
        future.get(5, TimeUnit.SECONDS)
    }
}
