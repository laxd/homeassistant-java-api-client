package uk.laxd.homeassistantclient.ws.message.model

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.trigger.JsonTrigger
import uk.laxd.homeassistantclient.model.json.ws.outgoing.TriggerWebSocketMessage
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class WebSocketMessageSerialisationTest extends Specification {

    private ObjectMapper objectMapper

    void setup() {
        objectMapper = new ObjectMapperFactory().createObjectMapper()
    }

    def "TriggerWebSocketMessage can be serialised correctly"() {
        given:
        def trigger = new JsonTrigger()
        trigger.type = "test"
        trigger.addAttribute("key", "value")

        def message = new TriggerWebSocketMessage(trigger)
        message.subscriptionId = 123

        when:
        def node = objectMapper.valueToTree(message)

        then:
        node.fieldNames().toList() == ["type", "id", "trigger"]
        node.get("type").textValue() == "subscribe_trigger"
        node.get("id").intValue() == 123
        node.get("trigger").toList()[0].get("platform").textValue() == "test"
        node.get("trigger").toList()[0].get("key").textValue() == "value"
    }
}
