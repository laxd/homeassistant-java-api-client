package uk.laxd.homeassistantclient.model.json.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty

class JsonService {

    JsonService(String serviceCall) {
        def parts = serviceCall.split("\\.")
        this.domain = parts[0]
        this.service = parts[1]
    }

    String type = "call_service"
    String domain
    String service

    @JsonProperty("service_data")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Map<String, Object> serviceData = [:]

    @JsonProperty("target")
    JsonTarget target = new JsonTarget()

}

class JsonTarget {
    @JsonProperty("entity_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<String> entities = []

    @JsonProperty("device_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<String> devices = []

    @JsonProperty("area_id")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    Collection<String> areas = []
}