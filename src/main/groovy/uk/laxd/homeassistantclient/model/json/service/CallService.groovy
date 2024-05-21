package uk.laxd.homeassistantclient.model.json.service

import com.fasterxml.jackson.annotation.JsonProperty

class CallService {

    CallService(String service) {
        this.domain = service.split("\\.")[0]
        this.service = service.split("\\.")[1]
    }

    @JsonProperty("type")
    String type = "call_service"

    @JsonProperty("service_data")
    Map<String, Object> serviceData = [:]

    @JsonProperty("domain")
    String domain

    @JsonProperty("service")
    String service

    @JsonProperty("target")
    ServiceTarget target = new ServiceTarget()

    // TODO: Only include this if the response is required
//    @JsonProperty("return_response")
//    boolean returnResponse = true

}
