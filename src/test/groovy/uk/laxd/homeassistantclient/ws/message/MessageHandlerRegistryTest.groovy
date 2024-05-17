package uk.laxd.homeassistantclient.ws.message

import spock.lang.Specification

class MessageHandlerRegistryTest extends Specification {
    def "message handlers are registered correctly"() {
        given:
        def messageHandler = new EventMessageHandler()

        when:
        def messageHandlerRegistry = new MessageHandlerRegistry([messageHandler])

        then:
        messageHandlerRegistry.getHandlers() == [messageHandler]
    }
}
