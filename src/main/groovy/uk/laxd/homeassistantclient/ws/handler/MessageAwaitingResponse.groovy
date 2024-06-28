package uk.laxd.homeassistantclient.ws.handler

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage

import java.util.concurrent.CompletableFuture

@Slf4j
@TupleConstructor
class MessageAwaitingResponse<M extends ResponseWebSocketMessage> {
    CompletableFuture<M> future
    Class<M> messageClass

    void respond(M message) {
        if (message.class == messageClass) {
            future.complete(message)
        }
        else {
            log.error("Future awaiting message with ID, but was wrong type. Expected $messageClass but got ${message.class}")
        }
    }
}
