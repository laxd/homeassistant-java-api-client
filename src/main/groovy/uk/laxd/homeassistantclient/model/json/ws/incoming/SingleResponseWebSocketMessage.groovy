package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonAnySetter
import groovy.transform.ToString
import groovy.util.logging.Slf4j

@Slf4j
@ToString
class SingleResponseWebSocketMessage extends ResponseWebSocketMessage {

    Map<String, Object> attributes = [:]

    @Override
    String getType() {
        attributes ? attributes.get("type") : null
    }

    @JsonAnySetter
    void setAttribute(String key, Object value) {
        log.debug("Setting attribute $key - $value")
        this.attributes[key] = value
    }

}
