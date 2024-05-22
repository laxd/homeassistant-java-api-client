package uk.laxd.homeassistantclient.model.json.trigger

import com.fasterxml.jackson.annotation.JsonAnyGetter
import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

class JsonTrigger {

    @JsonProperty("platform")
    String type

    @JsonIgnore
    private Map<String, Object> attributes = [:]

    void addAttribute(String key, Object value) {
        if (value != null) {
            attributes[key] = value
        }
    }

    @JsonAnyGetter
    @JsonIgnore
    Map<String, Object> getMap() {
        return attributes;
    }
}
