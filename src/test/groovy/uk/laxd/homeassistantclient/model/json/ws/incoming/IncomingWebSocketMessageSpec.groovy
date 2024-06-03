package uk.laxd.homeassistantclient.model.json.ws.incoming

import spock.lang.Specification
import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.json.event.StateChangedEvent
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.time.Duration
import java.time.OffsetDateTime
import java.time.ZoneOffset

class IncomingWebSocketMessageSpec extends Specification {

    def objectMapper = new ObjectMapperFactory().createObjectMapper()

    def "Event web socket message can be parsed"() {
        given:
        def incomingMessage = "{\"id\":18,\"type\":\"event\",\"event\":{\"data\":{\"entity_id\":\"light.bed_light\",\"new_state\":{\"entity_id\":\"light.bed_light\",\"last_changed\":\"2016-11-26T01:37:24.265390+00:00\",\"state\":\"on\",\"attributes\":{\"rgb_color\":[254,208,0],\"color_temp\":380,\"supported_features\":147,\"xy_color\":[0.5,0.5],\"brightness\":180,\"white_value\":200,\"friendly_name\":\"Bed Light\"},\"last_updated\":\"2016-11-26T01:37:24.265390+00:00\",\"context\":{\"id\":\"326ef27d19415c60c492fe330945f954\",\"parent_id\":null,\"user_id\":\"31ddb597e03147118cf8d2f8fbea5553\"}},\"old_state\":{\"entity_id\":\"light.bed_light\",\"last_changed\":\"2016-11-26T01:37:10.466994+00:00\",\"state\":\"off\",\"attributes\":{\"supported_features\":147,\"friendly_name\":\"Bed Light\"},\"last_updated\":\"2016-11-26T01:37:10.466994+00:00\",\"context\":{\"id\":\"e4af5b117137425e97658041a0538441\",\"parent_id\":null,\"user_id\":\"31ddb597e03147118cf8d2f8fbea5553\"}}},\"event_type\":\"state_changed\",\"time_fired\":\"2016-11-26T01:37:24.265429+00:00\",\"origin\":\"LOCAL\",\"context\":{\"id\":\"326ef27d19415c60c492fe330945f954\",\"parent_id\":null,\"user_id\":\"31ddb597e03147118cf8d2f8fbea5553\"}}}"

        when:
        def eventWebSocketMessage = objectMapper.readValue(incomingMessage, EventWebSocketMessage)

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
        stateChangedEvent.oldState.lastChanged == OffsetDateTime.of(2016, 11, 26, 01, 37, 10, 466994 * 1000, ZoneOffset.of("+0"))
        stateChangedEvent.newState.attributes == ["rgb_color": [254,208,0], "color_temp":380, "supported_features":147, "xy_color":[0.5, 0.5], "brightness":180, "white_value":200, "friendly_name":"Bed Light"]
        stateChangedEvent.oldState.attributes == ["supported_features":147, "friendly_name":"Bed Light"]
        stateChangedEvent.newState.state == "on"
        stateChangedEvent.context.id == "326ef27d19415c60c492fe330945f954"
        stateChangedEvent.origin == "LOCAL"
        stateChangedEvent.timeFired == OffsetDateTime.of(2016, 11, 26, 01, 37, 24, 265429 * 1000, ZoneOffset.of("+0"))
    }

    def "Trigger web socket message can be parsed"() {
        given:
        def incomingMessage = "{\"id\":3,\"type\":\"event\",\"event\":{\"variables\":{\"trigger\":{\"id\":\"0\",\"idx\":\"0\",\"alias\":null,\"platform\":\"state\",\"entity_id\":\"light.living_room_ceiling_1\",\"from_state\":{\"entity_id\":\"light.living_room_ceiling_1\",\"state\":\"on\",\"attributes\":{\"min_color_temp_kelvin\":2000,\"max_color_temp_kelvin\":6535,\"min_mireds\":153,\"max_mireds\":500,\"effect_list\":[\"None\",\"candle\",\"fire\",\"prism\"],\"supported_color_modes\":[\"color_temp\",\"xy\"],\"effect\":\"None\",\"color_mode\":\"color_temp\",\"brightness\":255,\"color_temp_kelvin\":2732,\"color_temp\":366,\"hs_color\":[28.327,64.71],\"rgb_color\":[255,167,89],\"xy_color\":[0.524,0.387],\"mode\":\"normal\",\"dynamics\":\"none\",\"friendly_name\":\"Living room - Ceiling 1\",\"supported_features\":44},\"last_changed\":\"2024-05-30T15:55:53.047706+00:00\",\"last_updated\":\"2024-05-30T15:55:53.047706+00:00\",\"context\":{\"id\":\"01HZ53DZ7WPTFB1YJTFMS8NGC9\",\"parent_id\":null,\"user_id\":\"bb0d573a3e1c4bad99a729987585dc2d\"}},\"to_state\":{\"entity_id\":\"light.living_room_ceiling_1\",\"state\":\"off\",\"attributes\":{\"min_color_temp_kelvin\":2000,\"max_color_temp_kelvin\":6535,\"min_mireds\":153,\"max_mireds\":500,\"effect_list\":[\"None\",\"candle\",\"fire\",\"prism\"],\"supported_color_modes\":[\"color_temp\",\"xy\"],\"effect\":null,\"color_mode\":null,\"brightness\":null,\"color_temp_kelvin\":null,\"color_temp\":null,\"hs_color\":null,\"rgb_color\":null,\"xy_color\":null,\"mode\":\"normal\",\"dynamics\":\"none\",\"friendly_name\":\"Living room - Ceiling 1\",\"supported_features\":44},\"last_changed\":\"2024-05-30T15:55:57.717510+00:00\",\"last_updated\":\"2024-05-30T15:55:57.717510+00:00\",\"context\":{\"id\":\"01HZ53E499AM8XQ2V9JDAPKAJZ\",\"parent_id\":null,\"user_id\":\"bb0d573a3e1c4bad99a729987585dc2d\"}},\"for\":{\"__type\":\"<class 'datetime.timedelta'>\",\"total_seconds\":5.0},\"attribute\":null,\"description\":\"state of light.living_room_ceiling_1\"}},\"context\":{\"id\":\"01HZ53E499AM8XQ2V9JDAPKAJZ\",\"parent_id\":null,\"user_id\":\"bb0d573a3e1c4bad99a729987585dc2d\"}}}"

        when:
        def eventWebSocketMessage = objectMapper.readValue(incomingMessage, EventWebSocketMessage)

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

}
