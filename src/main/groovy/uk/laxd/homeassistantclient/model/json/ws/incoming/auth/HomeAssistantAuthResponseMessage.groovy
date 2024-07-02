package uk.laxd.homeassistantclient.model.json.ws.incoming.auth

/**
 * A response to an authentication attempt, e.g. a {@code AuthenticationWebSocketMessage}
 */
abstract class HomeAssistantAuthResponseMessage extends HomeAssistantAuthMessage {

    /**
     * A potential message for e.g. authentication failures.
     */
    String message

    abstract boolean isSuccessful()
}
