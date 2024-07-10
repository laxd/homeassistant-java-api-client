package uk.laxd.homeassistantclient.ws

import jakarta.inject.Inject
import jakarta.inject.Named
import org.springframework.web.socket.WebSocketMessage
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResultWebSocketMessage
import uk.laxd.homeassistantclient.ws.handler.HomeAssistantWebSocketHandler
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.model.json.ws.outgoing.SubscriptionWebSocketMessage
import uk.laxd.homeassistantclient.ws.session.WebSocketSessionProvider

import java.util.concurrent.Future

@Named
class HomeAssistantWebSocketMessageDispatcher {

    private final IdGenerator idGenerator
    private final WebSocketSessionProvider webSocketSessionProvider
    private final HomeAssistantEventListenerRegistry registry
    private final JacksonWebSocketMessageConverter webSocketMessageConverter
    private final HomeAssistantWebSocketHandler webSocketHandler
    private final MessageResponseListener singleMessageResponseListener

    @Inject
    HomeAssistantWebSocketMessageDispatcher(IdGenerator idGenerator,
                                            WebSocketSessionProvider webSocketSessionProvider,
                                            HomeAssistantEventListenerRegistry registry,
                                            JacksonWebSocketMessageConverter webSocketMessageConverter,
                                            HomeAssistantWebSocketHandler webSocketHandler,
                                            MessageResponseListener singleMessageResponseListener) {
        this.idGenerator = idGenerator
        this.webSocketSessionProvider = webSocketSessionProvider
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.webSocketHandler = webSocketHandler
        this.singleMessageResponseListener = singleMessageResponseListener
    }

    <M extends ResponseWebSocketMessage> Future<M> sendMessageWithResponse(Integer id,
                                                                           WebSocketMessage message,
                                                                           Class<M> expectedResponseClass) {
        def future = singleMessageResponseListener.getResponse(id, expectedResponseClass)

        webSocketSessionProvider.authenticatedSession.sendMessage(message)

        future
    }

    Future<ResultWebSocketMessage> sendMessageWithListener(SubscriptionWebSocketMessage message,
                                                           HomeAssistantListener listener) {
        def id = idGenerator.generateId()
        message.subscriptionId = id
        registry.register(listener, id)

        sendMessageWithResponse(id, webSocketMessageConverter.toTextMessage(message), ResultWebSocketMessage)
    }

    /**
     * Send a message and wait for a response. The first message that is received that
     * contains the same generated ID of the message will be returned.
     */
    Future<ResponseWebSocketMessage> sendMessage(SubscriptionWebSocketMessage message) {
        message.subscriptionId = idGenerator.generateId()

        sendMessageWithResponse(
                message.subscriptionId,
                webSocketMessageConverter.toTextMessage(message),
                ResponseWebSocketMessage
        )
    }

    <M extends ResponseWebSocketMessage> Future<M> sendMessage(SubscriptionWebSocketMessage message,
                                                               Class<M> expectedResponseClass) {
        message.subscriptionId = idGenerator.generateId()

        sendMessageWithResponse(
                message.subscriptionId,
                webSocketMessageConverter.toTextMessage(message),
                expectedResponseClass
        )
    }

}
