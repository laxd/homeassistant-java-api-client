package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import groovy.transform.ToString
import uk.laxd.homeassistantclient.model.domain.trigger.For
import uk.laxd.homeassistantclient.model.json.event.jackson.TriggerEventDeserialiser

@ToString
@JsonDeserialize(using = TriggerEventDeserialiser)
class TriggerEvent extends Event {

    String platform
    String entityId
    State fromState
    State toState
    For duration
    Map<String, Object> attribute
    String description

}
