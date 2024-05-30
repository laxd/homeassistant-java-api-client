package uk.laxd.homeassistantclient.model.event

import com.fasterxml.jackson.databind.ObjectMapper
import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.event.StateChangedEvent
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventWebSocketMessage
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.time.LocalDateTime

class HomeAssistantEventModelSpec extends Specification {

    ObjectMapper mapper = new ObjectMapperFactory().createObjectMapper()

    def "State changed message can be parsed"() {
        given:
        def testJson = """
        {
           "type":"event",
           "event":{
              "event_type":"state_changed",
              "data":{
                 "entity_id":"sensor.bedroom_dehumidifier_current_consumption",
                 "old_state":{
                    "entity_id":"sensor.bedroom_dehumidifier_current_consumption",
                    "state":"179.7",
                    "attributes":{
                       "state_class":"measurement",
                       "unit_of_measurement":"W",
                       "device_class":"power",
                       "friendly_name":"Bedroom dehumidifier Current Consumption"
                    },
                    "last_changed":"2024-05-15T14:20:42.447228+00:00",
                    "last_updated":"2024-05-15T14:20:42.447228+00:00",
                    "context":{
                       "id":"01HXYA0Y2FC5849PZ358DC8DNG",
                       "parent_id":null,
                       "user_id":null
                    }
                 },
                 "new_state":{
                    "entity_id":"sensor.bedroom_dehumidifier_current_consumption",
                    "state":"179.8",
                    "attributes":{
                       "state_class":"measurement",
                       "unit_of_measurement":"W",
                       "device_class":"power",
                       "friendly_name":"Bedroom dehumidifier Current Consumption"
                    },
                    "last_changed":"2024-05-15T14:20:47.451108+00:00",
                    "last_updated":"2024-05-15T14:20:47.451108+00:00",
                    "context":{
                       "id":"01HXYA12YVP61S1AFAWF5JVZAY",
                       "parent_id":null,
                       "user_id":null
                    }
                 }
              },
              "origin":"LOCAL",
              "time_fired":"2024-05-15T14:20:47.451108+00:00",
              "context":{
                 "id":"01HXYA12YVP61S1AFAWF5JVZAY",
                 "parent_id":null,
                 "user_id":null
              }
           },
           "id":2
        }
        """

        when:
        def message = mapper.readValue(testJson, EventWebSocketMessage)
        def event = message.event as StateChangedEvent

        then:
        message.subscriptionId == 2
        event.entityId == "sensor.bedroom_dehumidifier_current_consumption"
        event.oldState.entityId == "sensor.bedroom_dehumidifier_current_consumption"
        event.oldState.lastChanged == LocalDateTime.of(2024, 05, 15, 14, 20, 42, 447228000)
        event.oldState.attributes.get("state_class") == "measurement"
        event.newState.state == "179.8"
        event.context.id == "01HXYA12YVP61S1AFAWF5JVZAY"
        event.origin == "LOCAL"
        event.timeFired == LocalDateTime.of(2024, 05, 15, 14, 20, 47, 451108000)
    }
}
