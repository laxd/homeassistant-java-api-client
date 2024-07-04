package uk.laxd.homeassistantclient.ws

import groovy.util.logging.Slf4j
import jakarta.inject.Named
import jakarta.inject.Singleton
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.IncomingWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResultWebSocketMessage
import uk.laxd.homeassistantclient.ws.handler.MessageAwaitingResponse
import uk.laxd.homeassistantclient.ws.handler.MessageCondition
import uk.laxd.homeassistantclient.ws.handler.MessageIdCondition
import uk.laxd.homeassistantclient.ws.handler.NoOpMessageCondition

import java.util.concurrent.*

@Named
@Singleton
@Slf4j
class MessageResponseListener {
    List<MessageAwaitingResponse> messagesAwaitingResponse = []

    void checkForMessagesAwaitingResponse(IncomingWebSocketMessage message) {
        def messageAwaitingResponse = messagesAwaitingResponse.find { it.condition.isValid(message) }

        if (messageAwaitingResponse) {
            log.debug("Found ${message.class.simpleName} matching ${messageAwaitingResponse.condition}")
            messagesAwaitingResponse.remove(messageAwaitingResponse)
            messageAwaitingResponse.respond(message)
        }
    }

    <M extends WebSocketMessage> Future<M> waitForMessage(Class<M> messageClass, MessageCondition<M> condition = null) {
        if (condition == null) {
            condition = new NoOpMessageCondition<M>()
        }

        log.debug("Waiting for ${messageClass.simpleName} with condition [$condition]")

        Future<M> future = new CompletableFuture<>()

        messagesAwaitingResponse += new MessageAwaitingResponse<>(future, messageClass, condition)

        future
    }

    /**
     * Used when sending a single message and expecting ONLY a single message in return e.g.
     * ping, get config, etc
     * @param responseId
     * @return
     */
    <M extends ResponseWebSocketMessage> Future<M> getResponse(Integer responseId, Class<M> expectedClass = ResponseWebSocketMessage) {
        waitForMessage(expectedClass, new MessageIdCondition(responseId))
    }

    /**
     * Used when e.g. setting up a listener to ensure it is set up before returning
     * @param resultId
     * @return
     */
    <M extends ResultWebSocketMessage> Future<M> getResult(Integer resultId, Class<M> expectedClass = ResultWebSocketMessage) {
        waitForMessage(expectedClass, new MessageIdCondition(resultId))
    }
}