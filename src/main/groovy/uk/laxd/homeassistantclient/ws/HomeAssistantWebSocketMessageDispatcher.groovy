package uk.laxd.homeassistantclient.ws

import jakarta.inject.Inject
import jakarta.inject.Named

import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.events.HomeAssistantListener
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResponseWebSocketMessage
import uk.laxd.homeassistantclient.model.json.ws.incoming.ResultWebSocketMessage
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
    private final SingleMessageResponseListener singleMessageResponseListener

    @Inject
    HomeAssistantWebSocketMessageDispatcher(IdGenerator idGenerator,
                                            WebSocketSessionProvider webSocketSessionProvider,
                                            HomeAssistantEventListenerRegistry registry,
                                            JacksonWebSocketMessageConverter webSocketMessageConverter,
                                            HomeAssistantWebSocketHandler webSocketHandler,
                                            SingleMessageResponseListener singleMessageResponseListener) {
        this.idGenerator = idGenerator
        this.webSocketSessionProvider = webSocketSessionProvider
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.webSocketHandler = webSocketHandler
        this.singleMessageResponseListener = singleMessageResponseListener
    }

    Future<ResultWebSocketMessage> sendMessageWithListener(SubscriptionWebSocketMessage message, HomeAssistantListener listener) {
        def id = idGenerator.generateId()
        message.subscriptionId = id
        registry.register(listener, id)

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))

        singleMessageResponseListener.getResult(id)
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

        singleMessageResponseListener.getResponse(message.subscriptionId)
    }


    <M extends ResponseWebSocketMessage> Future<M> sendMessage(SubscriptionWebSocketMessage message, Class<M> expectedResponseClass) {
        message.subscriptionId = idGenerator.generateId()

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))

        singleMessageResponseListener.getResponse(message.subscriptionId, expectedResponseClass)
    }



    void sendSingleMessage(WebSocketMessage message) {
        if (message instanceof SubscriptionWebSocketMessage) {
            message.subscriptionId = idGenerator.generateId()
        }

        webSocketSessionProvider.getOrCreateAuthenticatedSession()
                .sendMessage(webSocketMessageConverter.toTextMessage(message))
    }
}
