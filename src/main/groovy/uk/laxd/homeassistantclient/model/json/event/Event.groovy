package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.laxd.homeassistantclient.model.json.event.jackson.EventDeserialiser

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = EventDeserialiser)
class Event {

    @JsonProperty("context")
    Map<String, Object> context
}