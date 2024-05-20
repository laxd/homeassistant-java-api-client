package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.time.Duration

class TriggerSerialisationTest extends Specification {

    ObjectMapper objectMapper = new ObjectMapperFactory().createObjectMapper()

    def "minimal state trigger can be serialised"() {
        given:
        def trigger = new StateTrigger("light.bedroom")

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"entity_id":["light.bedroom"],"platform":"state"}"""
    }

    def "state trigger with all values can be serialised"() {
        given:
        def trigger = new StateTrigger("light.bedroom")
        trigger.from = "off"
        trigger.to = "on"
        trigger.duration = Duration.ofHours(1).plusMinutes(30)

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"from":"off","to":"on","entity_id":["light.bedroom"],"for":"01:30:00","platform":"state"}"""
    }

    def "minimal time trigger can be serialised"() {

        given:
        def trigger = new TimeTrigger("10:00:00")

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"at":["10:00:00"],"platform":"time"}"""
    }
    def "time trigger with all values can be serialised"() {

        given:
        def trigger = new TimeTrigger("10:00:00")
        trigger.at += "15:30"

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"at":["10:00:00","15:30"],"platform":"time"}"""
    }
}
