package uk.laxd.homeassistantclient.ws

import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Lazy
import org.springframework.stereotype.Component
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry

@Named
@Lazy
class HomeAssistantWebSocketClient {

    @Inject
    private IdGenerator idGenerator

    @Inject
    private WebSocketSession session

    @Inject
    private HomeAssistantEventListenerRegistry registry

    void listenToEvents(String event, HomeAssistantEventListener listener) {
        def messageId = idGenerator.generateId()
        listener.subscriptionId = messageId

        println("Subscribting to events")

        session.sendMessage(
                new TextMessage("""
            {
              "id": ${messageId},
              "type": "subscribe_events",
              "event_type": "$event"
            }
            """)
        )

        registry.register(listener)
    }

    void ping() {
        def msg = new TextMessage("""
            {
                "id": ${idGenerator.generateId()},
                "type": "ping"
            }
        """.stripIndent())
        println(msg.payload)
        session.sendMessage(msg)
    }

}

