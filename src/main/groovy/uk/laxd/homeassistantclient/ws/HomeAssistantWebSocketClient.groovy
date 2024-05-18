package uk.laxd.homeassistantclient.ws

import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.web.socket.TextMessage
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.ws.message.model.PingWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.TriggerWebSocketMessage

@Named
@Lazy
class HomeAssistantWebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantWebSocketClient)

    @Inject
    private IdGenerator idGenerator

    @Inject
    private WebSocketSession session

    @Inject
    private HomeAssistantEventListenerRegistry registry

    @Inject
    private JacksonWebSocketMessageConverter webSocketMessageConverter

    void listenToEvents(String event, HomeAssistantEventListener listener) {
        def messageId = idGenerator.generateId()
        listener.subscriptionId = messageId

        logger.info("Subscribing to events of type '{}', listener='{}'", event, listener)

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

    void listenToStateChange(String entity, String from, String to, HomeAssistantEventListener listener) {
        def messageId = idGenerator.generateId()
        listener.subscriptionId = messageId

        logger.info("Subscribing to state changes of '{}', from '{}' to '{}', listener='{}'", entity, from, to, listener)

        def message = new TriggerWebSocketMessage("state", entity, from, to)
        message.subscriptionId = messageId

        session.sendMessage(webSocketMessageConverter.toTextMessage(message))

        registry.register(listener)
    }

    void ping() {
        def msg = new PingWebSocketMessage()
        msg.subscriptionId = idGenerator.generateId()
        session.sendMessage(webSocketMessageConverter.toTextMessage(msg))
    }

}

