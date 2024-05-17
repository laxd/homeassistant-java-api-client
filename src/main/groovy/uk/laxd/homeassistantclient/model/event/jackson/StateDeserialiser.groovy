package uk.laxd.homeassistantclient.model.event.jackson

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import uk.laxd.homeassistantclient.model.event.Event
import uk.laxd.homeassistantclient.model.event.State
import uk.laxd.homeassistantclient.model.event.StateChangedEvent
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class StateDeserialiser extends JsonDeserializer<StateChangedEvent> {
    @Override
    StateChangedEvent deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        def mapper = new ObjectMapperFactory().createObjectMapper()

        JsonNode rootNode = jsonParser.getCodec().readTree(jsonParser)
        JsonNode dataNode = rootNode.path("data")
        JsonNode oldStateTree = dataNode.path("old_state")
        JsonNode newStateTree = dataNode.path("new_state")

        StateChangedEvent event = new StateChangedEvent()

        event.setEntityId(dataNode.get("entity_id").textValue())
        event.setOldState(mapper.treeToValue(oldStateTree, State))
        event.setNewState(mapper.treeToValue(newStateTree, State))

        // Populate superclass attributes too
        mapper.readerForUpdating(event).treeToValue(rootNode, Event)

        event
    }
}
