package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.domain.trigger.NumericStateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.StateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TemplateTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TimePatternTrigger
import uk.laxd.homeassistantclient.model.domain.trigger.TimeTrigger
import uk.laxd.homeassistantclient.model.mapper.trigger.NumericStateTriggerMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.StateTriggerMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TemplateTriggerMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TimePatternTriggerMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TimeTriggerMapper
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.time.Duration

class TriggerSerialisationTest extends Specification {

    ObjectMapper objectMapper = new ObjectMapperFactory().createObjectMapper()

    def "minimal state trigger can be serialised"() {
        given:
        def trigger = new StateTrigger("light.bedroom")

        def mapped = new StateTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList() == ["platform", "entity_id"]
        node.get("platform").textValue() == "state"
        node.get("entity_id").toList()[0].textValue() == "light.bedroom"
    }

    def "state trigger with all values can be serialised"() {
        given:
        def trigger = new StateTrigger("light.bedroom")
        trigger.from = "off"
        trigger.to = "on"
        trigger.duration = new For(Duration.ofHours(1).plusMinutes(30))

        def mapped = new StateTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "entity_id", "for", "from", "to"].sort()
        node.get("platform").textValue() == "state"
        node.get("entity_id").toList()[0].textValue() == "light.bedroom"
        node.get("from").textValue() == "off"
        node.get("to").textValue() == "on"
        node.get("for").textValue() == "01:30:00"
    }

    def "minimal time trigger can be serialised"() {
        given:
        def trigger = new TimeTrigger("10:00:00")

        def mapped = new TimeTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "at"].sort()
        node.get("platform").textValue() == "time"
        node.get("at").toList()[0].textValue() == "10:00:00"
    }

    def "time trigger with all values can be serialised"() {
        given:
        def trigger = new TimeTrigger("10:00:00")
        trigger.at += "15:30"

        def mapped = new TimeTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "at"].sort()
        node.get("platform").textValue() == "time"
        node.get("at").toList()*.textValue().sort() == ["10:00:00", "15:30"]
    }

    def "minimal time pattern trigger can be serialised"() {
        given:
        def trigger = new TimePatternTrigger()
        trigger.hours = "1"

        def mapped = new TimePatternTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "hours"].sort()
        node.get("platform").textValue() == "time_pattern"
        node.get("hours").textValue() == "1"
    }

    def "time pattern trigger with all values can be serialised"() {
        given:
        def trigger = new TimePatternTrigger()
        trigger.hours = "1"
        trigger.minutes = "/5"
        trigger.seconds = "*"

        def mapped = new TimePatternTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "hours", "minutes", "seconds"].sort()
        node.get("platform").textValue() == "time_pattern"
        node.get("hours").textValue() == "1"
        node.get("minutes").textValue() == "/5"
        node.get("seconds").textValue() == "*"
    }

    def "minimal value template trigger can be serialised"() {
        given:
        def trigger = new TemplateTrigger("{{ is_state('device_tracker.paulus', 'home') }}")

        def mapped = new TemplateTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "value_template"].sort()
        node.get("platform").textValue() == "template"
        node.get("value_template").textValue() == "{{ is_state('device_tracker.paulus', 'home') }}"
    }

    def "value template trigger with all values can be serialised"() {
        given:
        def trigger = new TemplateTrigger("{{ is_state('device_tracker.paulus', 'home') }}")
        trigger.duration = new For(Duration.ofMinutes(5))

        def mapped = new TemplateTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "value_template", "for"].sort()
        node.get("platform").textValue() == "template"
        node.get("value_template").textValue() == "{{ is_state('device_tracker.paulus', 'home') }}"
        node.get("for").textValue() == "00:05:00"
    }

    def "minimal numeric state trigger can be serialised"() {
        given:
        def trigger = new NumericStateTrigger("light.bedroom")

        def mapped = new NumericStateTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == ["platform", "entity_id"].sort()
        node.get("platform").textValue() == "numeric_state"
        node.get("entity_id").textValue() == "light.bedroom"
    }

    def "numeric state trigger with all values can be serialised"() {
        given:
        def trigger = new NumericStateTrigger("light.bedroom")
        trigger.below = 30
        trigger.above = 0
        trigger.attribute = "luminance"
        trigger.valueTemplate = "{{ state.attribute.luminance - 100 }}"

        def mapped = new NumericStateTriggerMapper().mapToJson(trigger)

        when:
        def node = objectMapper.valueToTree(mapped)

        then:
        node.fieldNames().toList().sort() == [
                "platform",
                "entity_id",
                "below",
                "above",
                "attribute",
                "value_template",
        ].sort()
        node.get("platform").textValue() == "numeric_state"
        node.get("entity_id").textValue() == "light.bedroom"
        node.get("below").textValue() == "30"
        node.get("above").textValue() == "0"
        node.get("attribute").textValue() == "luminance"
        node.get("value_template").textValue() == "{{ state.attribute.luminance - 100 }}"
    }

}
