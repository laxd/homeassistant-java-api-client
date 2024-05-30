package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

import com.fasterxml.jackson.annotation.JsonProperty


class HomeAssistantAuthSuccessfulMessage extends HomeAssistantAuthMessage {

    @JsonProperty("ha_version")
    String homeAssistantVersion

    @Override
    String getType() {
        "auth_ok"
    }
}
