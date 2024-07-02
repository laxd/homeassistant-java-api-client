package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

abstract class HomeAssistantAuthResponseMessage extends HomeAssistantAuthMessage {

    String message

    abstract boolean isSuccessful()
}
