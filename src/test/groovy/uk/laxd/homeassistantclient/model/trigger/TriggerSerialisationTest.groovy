package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.trigger.For
import uk.laxd.homeassistantclient.model.json.trigger.StateTrigger
import uk.laxd.homeassistantclient.model.json.trigger.TemplateTrigger
import uk.laxd.homeassistantclient.model.json.trigger.TimePatternTrigger
import uk.laxd.homeassistantclient.model.json.trigger.TimeTrigger
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
        trigger.duration = new For(Duration.ofHours(1).plusMinutes(30))

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"from":"off","to":"on","for":"01:30:00","entity_id":["light.bedroom"],"platform":"state"}"""
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

    def "minimal time pattern trigger can be serialised"() {
        given:
        def trigger = new TimePatternTrigger()
        trigger.hours = "1"

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"hours":"1","platform":"time_pattern"}"""
    }

    def "time pattern trigger with all values can be serialised"() {
        given:
        def trigger = new TimePatternTrigger()
        trigger.hours = "1"
        trigger.minutes = "/5"
        trigger.seconds = "*"

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"hours":"1","minutes":"/5","seconds":"*","platform":"time_pattern"}"""
    }

    def "minimal value template trigger can be serialised"() {
        given:
        def trigger = new TemplateTrigger("{{ is_state('device_tracker.paulus', 'home') }}")

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"value_template":"{{ is_state('device_tracker.paulus', 'home') }}","platform":"template"}"""
    }

    def "value template trigger with all values can be serialised"() {
        given:
        def trigger = new TemplateTrigger("{{ is_state('device_tracker.paulus', 'home') }}")
        trigger.duration = new For(Duration.ofMinutes(5))

        when:
        def triggerAsJson = objectMapper.writeValueAsString(trigger)

        then:
        triggerAsJson == """{"value_template":"{{ is_state('device_tracker.paulus', 'home') }}","for":"00:05:00","platform":"template"}"""
    }
}
