package uk.laxd.homeassistantclient.model.json.service

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.annotation.JsonProperty
import uk.laxd.homeassistantclient.model.domain.service.ServiceType

class JsonService {

    JsonService(ServiceType serviceType) {
        this.domain = serviceType.domain
        this.service = serviceType.service
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