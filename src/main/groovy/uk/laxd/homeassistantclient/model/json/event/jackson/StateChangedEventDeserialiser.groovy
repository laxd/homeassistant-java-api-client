package uk.laxd.homeassistantclient.model.json.event.jackson

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import uk.laxd.homeassistantclient.model.json.event.State
import uk.laxd.homeassistantclient.model.json.event.StateChangedEvent
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.time.OffsetDateTime

/**
 * Required to skip the "data" attribute in the JSON that is sent back from HA
 * Everything else is pretty much just doing what Jackson would normally do
 */
class StateChangedEventDeserialiser extends JsonDeserializer<StateChangedEvent> {

    @Override
    StateChangedEvent deserialize(JsonParser jsonParser, DeserializationContext ctxt)
            throws IOException, JacksonException {
        def mapper = new ObjectMapperFactory().createObjectMapper()

        JsonNode rootNode = jsonParser.codec.readTree(jsonParser)
        JsonNode dataNode = rootNode.path("data")
        JsonNode oldStateTree = dataNode.path("old_state")
        JsonNode newStateTree = dataNode.path("new_state")

        StateChangedEvent event = new StateChangedEvent()

        event.with {
            entityId = dataNode.get("entity_id").textValue()
            oldState = mapper.treeToValue(oldStateTree, State)
            newState = mapper.treeToValue(newStateTree, State)
            origin = rootNode.get("origin").textValue()
            timeFired = mapper.treeToValue(rootNode.get("time_fired"), OffsetDateTime)

            context = mapper.convertValue(rootNode.get("context"), new TypeReference<Map<String, Object>>(){ })
        }

        event
    }

}

