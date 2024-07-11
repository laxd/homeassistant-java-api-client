package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.json.event.StateChangedEvent
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.nio.file.Files
import java.nio.file.Paths
import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset

class IncomingWebSocketMessageSpec extends Specification {

    ObjectMapper objectMapper = new ObjectMapperFactory().createObjectMapper()

    def "Event web socket message can be parsed"() {
        given:
        def incomingMessage = parseMessageFile("eventWebSocketMessage")

        when:
        def eventWebSocketMessage = objectMapper.readValue(incomingMessage, EventResponseWebSocketMessage)

        then:
        eventWebSocketMessage.subscriptionId == 18
        eventWebSocketMessage.type == "event"
        eventWebSocketMessage.event instanceof StateChangedEvent

        when:
        def stateChangedEvent = eventWebSocketMessage.event as StateChangedEvent

        then:
        stateChangedEvent.oldState.state == "off"
        stateChangedEvent.newState.state == "on"
        stateChangedEvent.entityId == "light.bed_light"
        stateChangedEvent.oldState.entityId == "light.bed_light"
        stateChangedEvent.oldState.lastChanged == OffsetDateTime.of(
                2016,
                11,
                26,
                01,
                37,
                10,
                466994 * 1000, ZoneOffset.of("+0")
        )
        stateChangedEvent.newState.attributes == [
                "rgb_color": [254, 208, 0],
                "color_temp": 380,
                "supported_features": 147,
                "xy_color": [0.5, 0.5],
                "brightness": 180,
                "white_value": 200,
                "friendly_name": "Bed Light",
        ]
        stateChangedEvent.oldState.attributes == ["supported_features": 147, "friendly_name": "Bed Light"]
        stateChangedEvent.newState.state == "on"
        stateChangedEvent.context.id == "326ef27d19415c60c492fe330945f954"
        stateChangedEvent.origin == "LOCAL"
        stateChangedEvent.timeFired == OffsetDateTime.of(
                2016,
                11,
                26,
                01,
                37,
                24,
                265429 * 1000, ZoneOffset.of("+0")
        )
    }

    def "Trigger web socket message can be parsed"() {
        given:
        def incomingMessage = parseMessageFile("triggerWebSocketMessage")

        when:
        def eventWebSocketMessage = objectMapper.readValue(incomingMessage, EventResponseWebSocketMessage)

        then:
        eventWebSocketMessage.subscriptionId == 3
        eventWebSocketMessage.type == "event"
        eventWebSocketMessage.event instanceof TriggerEvent

        when:
        def triggerEvent = eventWebSocketMessage.event as TriggerEvent

        then:
        triggerEvent.platform == "state"
        triggerEvent.duration == new For(Duration.ofSeconds(5))
        triggerEvent.fromState.state == "on"
        triggerEvent.fromState.attributes["min_color_temp_kelvin"] == 2000
        triggerEvent.fromState.context.id == "01HZ53DZ7WPTFB1YJTFMS8NGC9"
        triggerEvent.toState.state == "off"
        triggerEvent.toState.attributes["max_color_temp_kelvin"] == 6535
        triggerEvent.toState.context.id == "01HZ53E499AM8XQ2V9JDAPKAJZ"
        triggerEvent.entityId == "light.living_room_ceiling_1"
    }

    private String parseMessageFile(String messageName) {
        def messageUri = Paths.get(ClassLoader.getSystemResource("wsMessages/${messageName}.json").toURI())
        Files.readString(messageUri)
    }

}
