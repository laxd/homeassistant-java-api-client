package uk.laxd.homeassistantclient

import uk.laxd.homeassistantclient.model.domain.entity.light.LightEntity

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

}
