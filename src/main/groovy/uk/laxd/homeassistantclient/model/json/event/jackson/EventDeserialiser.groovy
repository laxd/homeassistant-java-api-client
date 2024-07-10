package uk.laxd.homeassistantclient.model.json.event.jackson

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonParseException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.TreeNode
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import uk.laxd.homeassistantclient.model.json.event.Event
import uk.laxd.homeassistantclient.model.json.event.StateChangedEvent
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent

class EventDeserialiser extends JsonDeserializer<Event> {

    private static final String STATE_CHANGED_ATTRIBUTE = "data"
    private static final String TRIGGER_ATTRIBUTE = "variables"

    @Override
    Event deserialize(JsonParser p, DeserializationContext ctxt) throws IOException, JacksonException {
        TreeNode root = p.codec.readTree(p)

        if (root.has(STATE_CHANGED_ATTRIBUTE)) {
            p.codec.treeToValue(root, StateChangedEvent)
        }
        else if (root.has(TRIGGER_ATTRIBUTE)) {
            p.codec.treeToValue(root, TriggerEvent)
        }
        else {
            throw new JsonParseException(p, "Event has no recognisable attributes to deduce subtype.")
        }
    }

}

