package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

class HomeAssistantAuthFailedMessage extends HomeAssistantAuthResponseMessage {

    @Override
    String getType() {
        "auth_invalid"
    }

    @Override
    boolean isSuccessful() {
        false
    }
}
