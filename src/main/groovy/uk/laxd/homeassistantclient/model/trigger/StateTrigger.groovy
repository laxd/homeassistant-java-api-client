package uk.laxd.homeassistantclient.model.trigger

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.trigger.jackson.DurationSerialiser

import java.time.Duration

@ToString(includeNames = true, includes = "entities,from,to")
class StateTrigger extends Trigger {

    StateTrigger(String entity, String... additionalEntities) {
        this.entities = [entity] + Arrays.asList(additionalEntities)
    }

    @JsonProperty("entity_id")
    Collection<String> entities

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String from

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    String to

    @JsonProperty("for")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Duration duration

    @Override
    TriggerType triggerType() {
        TriggerType.STATE
    }
}
