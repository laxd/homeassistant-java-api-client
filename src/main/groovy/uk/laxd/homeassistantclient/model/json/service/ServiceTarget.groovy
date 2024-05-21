package uk.laxd.homeassistantclient.model.json.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

class ServiceTarget {

    @JsonProperty("area_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<String> areas = []

    @JsonProperty("device_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<String> devices = []

    @JsonProperty("entity_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<String> entities = []

}
