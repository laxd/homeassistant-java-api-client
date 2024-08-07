package uk.laxd.homeassistantclient.model.json.ws.incoming

import com.fasterxml.jackson.annotation.JsonAnySetter

/**
 * An IncomingWebSocketMessage that has not been mapped to a specific subclass,
 * and as such has no fields but captures every piece of data returned in the
 * data field
 */
class UnknownWebSocketMessage extends IncomingWebSocketMessage {

    Map<String, Object> data = [:]

    @JsonAnySetter
    void set(String name, Object value) {
        data[name] = value
    }

    final String type = "unknown"

}
