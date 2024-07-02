package uk.laxd.homeassistantclient.model.json

import spock.lang.Specification
import spock.lang.Unroll

class HomeAssistantEntitySpec extends Specification {

    @Unroll
    def "domain is parsed correctly"() {
        given:
        def entity = new HomeAssistantEntity()
        entity.entityId = entityId

        expect:
        entity.domain == expectedDomain

        where:
        entityId || expectedDomain
        "light.abc" || "light"
        "" || null
        "." || null
        "light.entity.abc" || "light"
    }
}
