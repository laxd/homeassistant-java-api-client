package uk.laxd.homeassistantclient.ws

import spock.lang.Specification
import uk.laxd.homeassistantclient.model.json.ws.incoming.PongWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.auth.HomeAssistantAuthRequiredMessage
import uk.laxd.homeassistantclient.ws.handler.MessageIdCondition

class MessageResponseListenerSpec extends Specification {

    private MessageResponseListener messageResponseListener = new MessageResponseListener()

    def "awaiting message is answered by matching incoming message"() {
        given:
        def future = messageResponseListener.waitForMessage(HomeAssistantAuthRequiredMessage)

        expect:
        !future.isDone()

        when:
        messageResponseListener.checkForMessagesAwaitingResponse(new HomeAssistantAuthRequiredMessage())

        then:
        future.isDone()
    }

    def "awaiting message with matching condition is answered by incoming message"() {
        given:
        def future = messageResponseListener.waitForMessage(PongWebSocketMessage, new MessageIdCondition(123))


        when:
        def incomingMessage = new PongWebSocketMessage()
        incomingMessage.subscriptionId = 999

        messageResponseListener.checkForMessagesAwaitingResponse(incomingMessage)

        then:
        !future.isDone()

        when:
        incomingMessage.subscriptionId = 123

        messageResponseListener.checkForMessagesAwaitingResponse(incomingMessage)

        then:
        future.isDone()
    }

    def "awaiting messages are removed from queue once answered"() {
        given:
        def future = messageResponseListener.waitForMessage(PongWebSocketMessage, new MessageIdCondition(123))

        expect:
        messageResponseListener.messagesAwaitingResponse.size() == 1

        when:
        def incomingMessage = new PongWebSocketMessage()
        incomingMessage.subscriptionId = 123

        messageResponseListener.checkForMessagesAwaitingResponse(incomingMessage)

        then:
        future.isDone()
        messageResponseListener.messagesAwaitingResponse.size() == 0
    }
}
