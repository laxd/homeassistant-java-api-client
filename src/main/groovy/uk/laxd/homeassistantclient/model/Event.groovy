package uk.laxd.homeassistantclient.model

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty

@JsonIgnoreProperties(ignoreUnknown = true)
class Event {

    @JsonProperty("event_type")
    String eventType

    @JsonProperty("data")
    Map<String, Object> data

}