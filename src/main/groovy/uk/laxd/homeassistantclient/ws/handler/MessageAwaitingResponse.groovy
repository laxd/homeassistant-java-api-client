package uk.laxd.homeassistantclient.ws.handler

import groovy.transform.TupleConstructor
import groovy.util.logging.Slf4j
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage

import java.util.concurrent.CompletableFuture

@Slf4j
@TupleConstructor
class MessageAwaitingResponse<M extends WebSocketMessage> {
    CompletableFuture<M> future
    Class<M> messageClass
    MessageCondition<M> condition

    MessageAwaitingResponse(CompletableFuture<M> future, Class<M> messageClass, MessageCondition<M> condition) {
        this.future = future
        this.messageClass = messageClass
        this.condition = condition
    }

    void respond(M message) {
        if(!condition.isValid(message)) {
            return
        }

        if (messageClass.isAssignableFrom(message.class)) {
            future.complete(message)
        }
        else {
            // We got a message that matches the condition, but the type of the message doesn't match what we
            // were expecting.
            // This shouldn't be an error, as e.g. we might have a confirmation message for setting up a listener
            // before we receive a message that actually corresponds to the listener
            log.warn("Awaiting message of type $messageClass, but got ${message.class} for matching condition")
        }
    }
}

interface MessageCondition<M extends WebSocketMessage> {
    boolean isValid(M message)
}

@TupleConstructor
class MessageIdCondition<M extends ResponseWebSocketMessage> implements MessageCondition<M> {
    Integer messageId

    @Override
    boolean isValid(ResponseWebSocketMessage message) {
        return message.subscriptionId == messageId
    }
}

class NoOpMessageCondition<M extends WebSocketMessage> implements MessageCondition<M> {
    @Override
    boolean isValid(M message) {
        return true
    }
}