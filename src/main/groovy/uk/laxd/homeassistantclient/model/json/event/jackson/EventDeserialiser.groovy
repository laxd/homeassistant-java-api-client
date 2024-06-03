package uk.laxd.homeassistantclient.model.json.event.jackson

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.ObjectMapper
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.StateChangedEvent
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

class EventDeserialiser extends JsonDeserializer<Event> {

    private static final String STATE_CHANGED_ATTRIBUTE = "data"
    private static final String TRIGGER_ATTRIBUTE = "variables"

    @Override
    Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        def root = p.getCodec().readTree(p)

        if (root.has(STATE_CHANGED_ATTRIBUTE)) {
            return p.getCodec().treeToValue(root, StateChangedEvent)
        }
        else if (root.has(TRIGGER_ATTRIBUTE)) {
            return p.getCodec().treeToValue(root, TriggerEvent)
        }
        else {
            throw new JsonParseException(p, "Event has no recognisable attributes to deduce subtype.")
        }
    }
}
