package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.trigger.builder.TriggerBuilder
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class WebSocketMessageSerialisationTest extends Specification {

    private ObjectMapper objectMapper

    void setup() {
        objectMapper = new ObjectMapperFactory().createObjectMapper()
    }

    def "TriggerWebSocketMessage can be serialised correctly"() {
        given:
        def message = new TriggerWebSocketMessage(
                TriggerBuilder.onStateChange("light.bedroom")
                        .from("off")
                        .to("on")
                        .build()
        )
        message.subscriptionId = 123

        when:
        def string = objectMapper.writeValueAsString(message)

        then:
        string == """{"type":"subscribe_trigger","id":123,"trigger":[{"from":"off","to":"on","entity_id":["light.bedroom"],"platform":"state"}]}"""
    }
}
