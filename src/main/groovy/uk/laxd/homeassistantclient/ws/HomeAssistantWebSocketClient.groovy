package uk.laxd.homeassistantclient.ws


import jakarta.inject.Inject
import jakarta.inject.Named
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Lazy
import org.springframework.web.socket.WebSocketSession
import uk.laxd.homeassistantclient.events.HomeAssistantEventListener
import uk.laxd.homeassistantclient.events.HomeAssistantEventListenerRegistry
import uk.laxd.homeassistantclient.model.domain.service.Service
import uk.laxd.homeassistantclient.model.domain.service.ServiceTarget
import uk.laxd.homeassistantclient.model.domain.service.ServiceType
import uk.laxd.homeassistantclient.model.domain.service.TargetType
import uk.laxd.homeassistantclient.model.domain.trigger.Trigger
import uk.laxd.homeassistantclient.model.mapper.service.ServiceMapper
import uk.laxd.homeassistantclient.model.mapper.trigger.TriggerMapperFactory
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
    private TriggerMapperFactory triggerMapperFactory
    private ServiceMapper serviceMapper

    @Inject
    HomeAssistantWebSocketClient(WebSocketSubscriptionIdPopulator idPopulator,
                                 IdGenerator idGenerator,
                                 WebSocketSession session,
                                 HomeAssistantEventListenerRegistry registry,
                                 JacksonWebSocketMessageConverter webSocketMessageConverter,
                                 TriggerMapperFactory triggerMapperFactory,
                                 ServiceMapper serviceMapper) {
        this.idPopulator = idPopulator
        this.idGenerator = idGenerator
        this.session = session
        this.registry = registry
        this.webSocketMessageConverter = webSocketMessageConverter
        this.triggerMapperFactory = triggerMapperFactory
        this.serviceMapper = serviceMapper
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

        def jsonTriggers = triggers.collect {
            triggerMapperFactory.getTriggerMapperForTrigger(it).mapToJson(it)
        }

        def message = new TriggerWebSocketMessage(jsonTriggers)

        idPopulator.linkMessageToListener(message, listener)
        session.sendMessage(webSocketMessageConverter.toTextMessage(message))

        registry.register(listener)
    }

    void turnOn(String entityId) {
        def service = new Service(ServiceType.TURN_ON)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    void turnOff(String entityId) {
        def service = new Service(ServiceType.TURN_OFF)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    void toggle(String entityId) {
        def service = new Service(ServiceType.TOGGLE)
        service.serviceTargets << new ServiceTarget(TargetType.ENTITY, entityId)
        callService(service)
    }

    void callService(Service service) {
        logger.info("Calling service {}", service)

        def jsonService = serviceMapper.map(service)

        def message = new CallServiceWebSocketMessage(jsonService)
        message.subscriptionId = idGenerator.generateId()

        session.sendMessage(webSocketMessageConverter.toTextMessage(message))
    }

    void ping() {
        def msg = new PingWebSocketMessage()
        msg.subscriptionId = idGenerator.generateId()
        session.sendMessage(webSocketMessageConverter.toTextMessage(msg))
    }

}

