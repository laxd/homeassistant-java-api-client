package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

class HomeAssistantAuthFailedMessage extends HomeAssistantAuthResponseMessage {

    final String type = "auth_invalid"

    @Override
    boolean isSuccessful() {
        false
    }

}
