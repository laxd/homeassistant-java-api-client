package uk.laxd.homeassistantclient.ws

import jakarta.inject.Inject
import jakarta.inject.Named
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage
import uk.laxd.homeassistantclient.ws.handler.HomeAssistantWebSocketHandler
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.model.json.ws.outgoing.SubscriptionWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.WebSocketMessage
import uk.laxd.homeassistantclient.ws.session.WebSocketSessionProvider

import java.util.concurrent.Future

@Named
class HomeAssistantWebSocketMessageDispatcher {

    private final IdGenerator idGenerator
    private final WebSocketSessionProvider webSocketSessionProvider
    private final HomeAssistantEventListenerRegistry registry
    private final JacksonWebSocketMessageConverter webSocketMessageConverter
    private final HomeAssistantWebSocketHandler webSocketHandler
    private final OneShotMessageNotifier oneShotMessageNotifier

    @Inject
    HomeAssistantWebSocketMessageDispatcher(IdGenerator idGenerator,
                                            WebSocketSessionProvider webSocketSessionProvider,
                                            HomeAssistantEventListenerRegistry registry,
                                            JacksonWebSocketMessageConverter webSocketMessageConverter,
                                            HomeAssistantWebSocketHandler webSocketHandler,
                                            OneShotMessageNotifier oneShotMessageNotifier) {
        this.idGenerator = idGenerator
        this.webSocketSessionProvider = webSocketSessionProvider
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.webSocketHandler = webSocketHandler
        this.oneShotMessageNotifier = oneShotMessageNotifier
    }

    void sendMessageWithResponseListener(SubscriptionWebSocketMessage message, HomeAssistantEventListener listener) {
        def id = idGenerator.generateId()
        message.subscriptionId = id
        registry.register(listener, id)

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))
    }

    /**
     * Send a message and wait for a response. The first message that is received that
     * contains the same generated ID of the message will be returned.
     * @param message
     * @return
     */
    Future<ResponseWebSocketMessage> sendMessage(SubscriptionWebSocketMessage message) {
        message.subscriptionId = idGenerator.generateId()

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))

        oneShotMessageNotifier.waitForMessageWithId(message.subscriptionId)
    }

    void sendSingleMessage(WebSocketMessage message) {
        if (message instanceof SubscriptionWebSocketMessage) {
            message.subscriptionId = idGenerator.generateId()
        }

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))
    }
}
