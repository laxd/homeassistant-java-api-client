package uk.laxd.homeassistantclient.model.ws

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.model.json.ws.HomeAssistantWebSocketMessage
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
        def message = mapper.readValue(testJson, HomeAssistantWebSocketMessage)

        then:
        message instanceof HomeAssistantAuthRequiredMessage
        message.unknown["ha_version"] == "2021.5.3"
    }
}
