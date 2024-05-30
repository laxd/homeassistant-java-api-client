package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

class HomeAssistantAuthFailedMessage extends HomeAssistantAuthMessage {
    @Override
    String getType() {
        "auth_invalid"
    }
}
