package uk.laxd.homeassistantclient.ws.handler

import groovy.util.logging.Slf4j
import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.model.json.ws.incoming.EventWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.ws.MessageResponseListener

/**
 * Handles returning the response from a single message to the caller.
 */
@Named
@Slf4j
class MessageResponseHandler implements MessageHandler<IncomingWebSocketMessage> {

    private final MessageResponseListener singleMessageResponseListener

    @Inject
    MessageResponseHandler(MessageResponseListener singleMessageResponseListener) {
        this.singleMessageResponseListener = singleMessageResponseListener
    }


    @Override
    void handle(WebSocketSession session, IncomingWebSocketMessage message) {
        singleMessageResponseListener.checkForMessagesAwaitingResponse(message)
    }

    @Override
    boolean canHandle(IncomingWebSocketMessage message) {
        // Any type of response can be handled by the SingleMessageResponseListener, but
        // we don't want to handle EventWebSocketMessages in case we miss the initial ResultWebSocketMessage
        // when subscribing to Events/Triggers
        !(message instanceof EventWebSocketMessage)
    }
}
