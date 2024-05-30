package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class HomeAssistantWsModelSpec extends Specification {

    ObjectMapper mapper = new ObjectMapperFactory().createObjectMapper()

    def "setup"() {
        mapper.registerModule(new Jdk8Module())
    }

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
}
