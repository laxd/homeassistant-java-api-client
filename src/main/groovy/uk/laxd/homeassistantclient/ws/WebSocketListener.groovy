package uk.laxd.homeassistantclient.ws

import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage

/**
 * A listener that listens to {@link WebSocketMessage}s coming from the Home Assistant server.
 *
 * No filtering is done and all web socket messages are passed through to the {@link #handle} method
 */
abstract class WebSocketListener implements HomeAssistantListener<WebSocketMessage> {
    @Override
    abstract void handle(WebSocketMessage object)
}
