package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

import com.fasterxml.jackson.annotation.JsonProperty

class HomeAssistantAuthRequiredMessage extends HomeAssistantAuthMessage {

    @JsonProperty("ha_version")
    String homeAssistantVersion

    final String type = "auth_required"

}
