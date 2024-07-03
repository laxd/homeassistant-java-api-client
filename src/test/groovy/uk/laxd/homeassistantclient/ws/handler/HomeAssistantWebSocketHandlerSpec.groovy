package uk.laxd.homeassistantclient.ws.handler

import org.springframework.web.socket.TextMessage
import spock.lang.Specification
import uk.laxd.homeassistantclient.spring.ObjectMapperFactory

class HomeAssistantWebSocketHandlerSpec extends Specification {

    def objectMapper = new ObjectMapperFactory().createObjectMapper()

    def "single message can be parsed"() {
        given:
        def messageHandlerDelegate = Mock(MessageHandlerDelegate)
        def handler = new HomeAssistantWebSocketHandler(objectMapper, messageHandlerDelegate)

        def jsonMessage = '{"id":1,"type":"pong"}'

        def message = new TextMessage(jsonMessage, true)

        when:
        handler.handleMessage(null, message)

        then:
        1 * messageHandlerDelegate.handle(_, { it.type == "pong" && it.subscriptionId == 1 })
    }

    def "multipart message can be parsed"() {
        given:
        def messageHandlerDelegate = Mock(MessageHandlerDelegate)
        def handler = new HomeAssistantWebSocketHandler(objectMapper, messageHandlerDelegate)

        def jsonMessage1 = '{"id":1,'
        def jsonMessage2 = '"type":"pong"}'

        def message1 = new TextMessage(jsonMessage1, false)
        def message2 = new TextMessage(jsonMessage2, true)

        when:
        handler.handleMessage(null, message1)
        handler.handleMessage(null, message2)

        then:
        1 * messageHandlerDelegate.handle(_, { it.type == "pong" && it.subscriptionId == 1 })
    }
}
