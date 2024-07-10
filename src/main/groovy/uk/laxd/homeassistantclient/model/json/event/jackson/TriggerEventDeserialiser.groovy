package uk.laxd.homeassistantclient.model.json.event.jackson

import com.fasterxml.jackson.core.JacksonException
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonDeserializer
import com.fasterxml.jackson.databind.JsonNode
import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.json.event.State
import uk.laxd.homeassistantclient.model.json.event.TriggerEvent
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

import java.time.Duration

/**
 * Required to skip the "variables + trigger" attribute in the JSON that is sent back from HA
 * Everything else is pretty much just doing what Jackson would normally do
 */
class TriggerEventDeserialiser extends JsonDeserializer<TriggerEvent> {

    @Override
    TriggerEvent deserialize(JsonParser jsonParser, DeserializationContext ctxt) throws IOException, JacksonException {
        def mapper = new ObjectMapperFactory().createObjectMapper()

        JsonNode rootNode = jsonParser.codec.readTree(jsonParser)
        JsonNode triggerNode = rootNode.path("variables").path("trigger")
        JsonNode fromStateTree = triggerNode.path("from_state")
        JsonNode toStateTree = triggerNode.path("to_state")

        TriggerEvent event = new TriggerEvent()

        event.platform = triggerNode.get("platform").textValue()
        event.entityId = triggerNode.get("entity_id").textValue()
        event.fromState = mapper.treeToValue(fromStateTree, State)
        event.toState = mapper.treeToValue(toStateTree, State)
        event.description = triggerNode.get("description").textValue()

        def totalSeconds = mapper.treeToValue(triggerNode.get("for").get("total_seconds"), Integer)

        if (totalSeconds) {
            event.duration = new For(Duration.ofSeconds(totalSeconds))
        }

        event.context = mapper.convertValue(rootNode.get("context"), new TypeReference<Map<String, Object>>(){ })

        event
    }

}

