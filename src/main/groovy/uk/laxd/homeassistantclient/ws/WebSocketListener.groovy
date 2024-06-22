package uk.laxd.homeassistantclient.ws

import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage

/**
 * A listener that listens to {@link WebSocketMessage}s coming from the Home Assistant server.
 *
 * The {@link WebSocketMessage} is passed to the handle message without any filtering
 */
abstract class WebSocketListener<M extends WebSocketMessage> implements HomeAssistantListener<M> {
    @Override
    abstract void handle(M object)
}
