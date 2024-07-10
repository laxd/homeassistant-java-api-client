package uk.laxd.homeassistantclient.ws.handler

import spock.lang.Specification
import uk.laxd.homeassistantclient.model.domain.response.HomeAssistantPongMessage
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthResponseMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthSuccessfulMessage

import java.util.concurrent.CompletableFuture

class MessageAwaitingResponseSpec extends Specification {

    def "message class is checked before future completed"() {
        given:
        def future = new CompletableFuture()
        def awaitingResponse = new MessageAwaitingResponse(future, messageClass, { true })

        when:
        awaitingResponse.respond(message)

        then:
        future.isDone() == result

        where:
        messageClass | message | result
        HomeAssistantAuthResponseMessage | new HomeAssistantAuthSuccessfulMessage() | true
        HomeAssistantAuthSuccessfulMessage | new HomeAssistantAuthSuccessfulMessage() | true
        HomeAssistantPongMessage | new HomeAssistantAuthSuccessfulMessage() | false
    }

    def "message condition is checked before future is completed"() {
        given:
        def future = new CompletableFuture()
        def awaitingResponse = new MessageAwaitingResponse(future, WebSocketMessage, condition)

        when:
        awaitingResponse.respond(new HomeAssistantAuthSuccessfulMessage())

        then:
        future.isDone() == result

        where:
        condition | result
        ({ true }) | true
        ({ false }) | false
    }
}
