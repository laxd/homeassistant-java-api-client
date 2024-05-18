package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class WebSocketMessageSerialisationTest extends Specification {

    private ObjectMapper objectMapper

    void setup() {
        objectMapper = new ObjectMapperFactory().createObjectMapper()
    }

    def "TriggerWebSocketMessage can be serialised correctly"() {
        given:
        def message = new TriggerWebSocketMessage(
                "state",
                "light.bedroom",
                "off",
                "on"
        )
        message.subscriptionId = 123

        when:
        def string = objectMapper.writeValueAsString(message)

        then:
        string == """{"type":"subscribe_trigger","id":123,"trigger":{"platform":"state","entity_id":"light.bedroom","from":"off","to":"on"}}"""
    }
}