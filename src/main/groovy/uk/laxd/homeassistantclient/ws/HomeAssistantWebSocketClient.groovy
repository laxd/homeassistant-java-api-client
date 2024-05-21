package uk.laxd.homeassistantclient.ws

import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.json.service.CallService
import uk.laxd.homeassistantclient.model.json.service.ServiceType
import uk.laxd.homeassistantclient.model.json.trigger.Trigger
import uk.laxd.homeassistantclient.ws.message.WebSocketSubscriptionIdPopulator
import uk.laxd.homeassistantclient.ws.message.model.CallServiceWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.EventWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.JacksonWebSocketMessageConverter
import uk.laxd.homeassistantclient.ws.message.model.PingWebSocketMessage
import uk.laxd.homeassistantclient.ws.message.model.TriggerWebSocketMessage

@Named
@Lazy
class HomeAssistantWebSocketClient {

    private static final Logger logger = LoggerFactory.getLogger(HomeAssistantWebSocketClient)

    private WebSocketSubscriptionIdPopulator idPopulator
    private IdGenerator idGenerator
    private WebSocketSession session
    private HomeAssistantEventListenerRegistry registry
    private JacksonWebSocketMessageConverter webSocketMessageConverter

    @Inject
    HomeAssistantWebSocketClient(WebSocketSubscriptionIdPopulator idPopulator,
                                 IdGenerator idGenerator,
                                 WebSocketSession session,
                                 HomeAssistantEventListenerRegistry registry,
                                 JacksonWebSocketMessageConverter webSocketMessageConverter) {
        this.idPopulator = idPopulator
        this.idGenerator = idGenerator
        this.session = session
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
    }

    void listenToEvents(String event, HomeAssistantEventListener listener) {
        logger.info("Subscribing to events of type '{}', listener='{}'", event, listener)

        def message = new EventWebSocketMessage(event)

        idPopulator.linkMessageToListener(message, listener)
        session.sendMessage(webSocketMessageConverter.toTextMessage(message))

        registry.register(listener)
    }

    void listenToTrigger(Trigger trigger, HomeAssistantEventListener listener) {
        listenToTriggers([trigger], listener)
    }

    void listenToTriggers(Collection<Trigger> triggers, HomeAssistantEventListener listener) {
        logger.info("Subscribing with triggers [{}], listener='{}'", triggers, listener)

        def message = new TriggerWebSocketMessage(triggers)

        idPopulator.linkMessageToListener(message, listener)
        session.sendMessage(webSocketMessageConverter.toTextMessage(message))

        registry.register(listener)
    }

    void turnOn(String entityId) {
        def service = new CallService(ServiceType.TURN_ON.text)
        service.target.entities << entityId
        callService(service)
    }

    void turnOff(String entityId) {
        def service = new CallService(ServiceType.TURN_OFF.text)
        service.target.entities << entityId
        callService(service)
    }

    void toggle(String entityId) {
        def service = new CallService(ServiceType.TOGGLE.text)
        service.target.entities << entityId
        callService(service)
    }

    void callService(CallService callService) {
        logger.info("Calling service {}", callService)

        def message = new CallServiceWebSocketMessage(callService)
        message.subscriptionId = idGenerator.generateId()

        session.sendMessage(webSocketMessageConverter.toTextMessage(message))
    }

    void ping() {
        def msg = new PingWebSocketMessage()
        msg.subscriptionId = idGenerator.generateId()
        session.sendMessage(webSocketMessageConverter.toTextMessage(msg))
    }

}

