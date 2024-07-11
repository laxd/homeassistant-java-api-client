package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity
import uk.laxd.homeassistantclient.model.domain.entity.state.State

class EntityIntegrationTest extends AbstractIntegrationTest {

    def "Test entity state returned"() {
        given:

        when:
        def kitchenLight = client.getEntity("light.kitchen", LightEntity)
        kitchenLight.turnOff()

        then:
        waitUntil {
            client.getEntity("light.kitchen", LightEntity).state == State.OFF
        }

        when:
        kitchenLight.turnOn()

        then:
        waitUntil {
            client.getEntity("light.kitchen", LightEntity).state == State.ON
        }
    }

}
