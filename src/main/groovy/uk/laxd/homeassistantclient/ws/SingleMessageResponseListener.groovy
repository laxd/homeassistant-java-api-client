package uk.laxd.homeassistantclient.ws

import groovy.util.logging.Slf4j
import jakarta.inject.Named
import jakarta.inject.Singleton
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResultWebSocketMessage
import uk.laxd.homeassistantclient.ws.handler.MessageAwaitingResponse

import java.util.concurrent.*

@Named
@Singleton
@Slf4j
class SingleMessageResponseListener {
    Map<Integer, MessageAwaitingResponse> messagesAwaitingResponse = [:]

    void checkResponseMessage(ResponseWebSocketMessage message) {
        log.debug("Checking message ${message.subscriptionId} of type ${message.class} for listeners")
        log.debug("Current messages awaiting response: $messagesAwaitingResponse")

        def messageAwaitingResponse = messagesAwaitingResponse.remove(message.subscriptionId)
        if (messageAwaitingResponse) {
            log.debug("Found ResponseWebSocketMessage response with ID ${message.subscriptionId}")
            messageAwaitingResponse.respond(message)
        }
    }

    <M extends ResponseWebSocketMessage> Future<M> getResponse(Integer responseId, Class<M> messageClass) {
        log.debug("Awaiting message of type $messageClass with ID $responseId")

        Future<M> future = new CompletableFuture<>()

        messagesAwaitingResponse[responseId] = new MessageAwaitingResponse<>(future, messageClass)

        future
    }

    /**
     * Used when sending a single message and expecting ONLY a single message in return e.g.
     * ping, get config, etc
     * @param responseId
     * @return
     */
    Future<ResponseWebSocketMessage> getResponse(Integer responseId) {
        getResponse(responseId, ResponseWebSocketMessage)
    }

    /**
     * Used when e.g. setting up a listener to ensure it is set up before returning
     * @param resultId
     * @return
     */
    Future<ResultWebSocketMessage> getResult(Integer resultId) {
        getResponse(resultId, ResultWebSocketMessage)
    }
}