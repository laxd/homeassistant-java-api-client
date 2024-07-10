package uk.laxd.homeassistantclient.model.json.event

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import uk.laxd.homeassistantclient.model.json.Response
import uk.laxd.homeassistantclient.model.json.event.jackson.EventDeserialiser

/**
 * Represents an event originating from the server in response to a trigger, or state change
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(using = EventDeserialiser)
class Event implements Response {

    @JsonProperty("context")
    Map<String, Object> context

}
