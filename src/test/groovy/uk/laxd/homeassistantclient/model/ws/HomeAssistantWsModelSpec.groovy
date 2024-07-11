package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import spock.lang.Unroll
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventResponseWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.PongWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResultWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.UnknownWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthFailedMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthSuccessfulMessage
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class HomeAssistantWsModelSpec extends Specification {

    ObjectMapper mapper = new ObjectMapperFactory().createObjectMapper()

    def "auth message can be parsed"() {
        given:
        def testJson = """
        {
          "type": "auth_required",
          "ha_version": "2021.5.3"
        }
        """

        when:
        def message = mapper.readValue(testJson, IncomingWebSocketMessage)

        then:
        message instanceof HomeAssistantAuthRequiredMessage && message.homeAssistantVersion == "2021.5.3"
    }

    @Unroll
    def "message with type=#type get parsed to #messageClass"() {
        given:
        def json = """
        {
            "type": "$type"
        }
        """

        expect:
        mapper.readValue(json, IncomingWebSocketMessage).class == messageClass

        where:
        type | messageClass
        "auth_required" | HomeAssistantAuthRequiredMessage
        "auth_ok" | HomeAssistantAuthSuccessfulMessage
        "auth_invalid" | HomeAssistantAuthFailedMessage
        "event" | EventResponseWebSocketMessage
        "pong" | PongWebSocketMessage
        "result" | ResultWebSocketMessage
        "qwertyuiop" | UnknownWebSocketMessage
    }

}
